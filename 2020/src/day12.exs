defmodule Day12 do
  @behaviour Runner.Day

  import Kernel, except: [apply: 2, apply: 3]

  def read(file) do
    File.stream!(file)
    |> Stream.map(&String.trim/1)
    |> Stream.map(fn s -> {String.at(s, 0), String.slice(s, 1..-1) |> String.to_integer()} end)
  end

  defmodule Location do
    def apply(%{x: x} = s, {"E", a}), do: %{s | x: x + a}
    def apply(%{x: x} = s, {"W", a}), do: %{s | x: x - a}
    def apply(%{y: y} = s, {"N", a}), do: %{s | y: y + a}
    def apply(%{y: y} = s, {"S", a}), do: %{s | y: y - a}
  end

  defmodule Ship do
    defstruct ~w(angle x y)a

    def resolve_direction(0), do: "E"
    def resolve_direction(90), do: "N"
    def resolve_direction(180), do: "W"
    def resolve_direction(270), do: "S"

    defp fix_degrees(a) when a >= 0, do: rem(a, 360)
    defp fix_degrees(a) when a < 0, do: fix_degrees(360 + a)

    def apply(%{angle: a} = s, {"L", d}), do: %{s | angle: fix_degrees(a + d)}
    def apply(%{angle: a} = s, {"R", d}), do: %{s | angle: fix_degrees(a - d)}
    def apply(%Ship{angle: a} = s, {"F", f}), do: apply(s, {resolve_direction(a), f})
    defdelegate apply(s, a), to: Location

    def distance_from_center(%Ship{x: x, y: y}), do: abs(x) + abs(y)
  end

  @impl true
  def part1(file) do
    read(file)
    |> Enum.reduce(%Ship{angle: 0, x: 0, y: 0}, fn x, a -> Ship.apply(a, x) end)
    |> Ship.distance_from_center()
    |> IO.inspect()
  end

  defmodule Waypoint do
    defstruct ~w(x y)a

    def apply(%Waypoint{x: x, y: y} = w, ship, {"L", 90}), do: {%{w | x: -y, y: x}, ship}
    def apply(%Waypoint{x: x, y: y} = w, ship, {"L", 180}), do: {%{w | x: -x, y: -y}, ship}
    def apply(%Waypoint{x: x, y: y} = w, ship, {"L", 270}), do: {%{w | x: y, y: -x}, ship}

    def apply(w, s, {"R", a}), do: apply(w, s, {"L", 360 - a})

    def apply(%Waypoint{x: x, y: y} = w, s, {"F", a}) do
      new_ship =
        s
        |> Ship.apply({"N", x * a})
        |> Ship.apply({"E", y * a})

      {w, new_ship}
    end

    def apply(w, s, a) do
      {Location.apply(w, a), s}
    end
  end

  @impl true
  def part2(file) do
    read(file)
    |> Enum.reduce({%Waypoint{x: 10, y: 1}, %Ship{angle: 0, x: 0, y: 0}}, fn x, {w, s} ->
      Waypoint.apply(w, s, x)
    end)
    |> elem(1)
    |> Ship.distance_from_center()
    |> IO.inspect()
  end
end

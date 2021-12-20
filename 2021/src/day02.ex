defmodule Day02 do
  @behaviour Day

  def split_command(str) do
    [command, number] = String.split(str, " ")
    [command, String.to_integer(number)]
  end

  def apply_command1(["forward", x], [h, d]), do: [h + x, d]
  def apply_command1(["up", x], [h, d]), do: [h, d - x]
  def apply_command1(["down", x], [h, d]), do: [h, d + x]

  @impl true
  def part1(file) do
    File.read!(file)
    |> String.split("\n", trim: true)
    |> Enum.map(&split_command/1)
    |> Enum.reduce([0, 0], &apply_command1/2)
    |> Enum.reduce(fn a, b -> a * b end)
  end

  def apply_command2(["forward", x], [h, d, a]), do: [h + x, d + x * a, a]
  def apply_command2(["up", x], [h, d, a]), do: [h, d, a - x]
  def apply_command2(["down", x], [h, d, a]), do: [h, d, a + x]

  @impl true
  def part2(file) do
    [h, d, _] =
      File.read!(file)
      |> String.split("\n", trim: true)
      |> Enum.map(&split_command/1)
      |> Enum.reduce([0, 0, 0], &apply_command2/2)

    h * d
  end
end

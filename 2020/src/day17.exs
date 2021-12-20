defmodule Day17 do
  @behaviour Runner.Day

  defp mapper3(x, {val, y}) do
    {{x, y, 0}, val == "#"}
  end

  defp mapper4(x, {val, y}) do
    {{x, y, 0, 0}, val == "#"}
  end

  def read(file, mapper) do
    File.stream!(file)
    |> Stream.map(&String.trim/1)
    |> Stream.with_index()
    |> Stream.flat_map(fn {line, x} ->
      elements = String.graphemes(line) |> Enum.with_index()
      Enum.map(elements, fn v -> mapper.(x, v) end)
    end)
    |> Map.new()
  end

  def at(grid, position) do
    Map.get(grid, position, false)
  end

  def neighbours(grid, pos) do
    neighbour_pos(pos)
    |> Enum.map(fn pos -> at(grid, pos) end)
  end

  def neighbour_pos({xo, yo, zo}) do
    for x <- -1..1, y <- -1..1, z <- -1..1, {x, y, z} != {0, 0, 0} do
      {xo + x, yo + y, zo + z}
    end
  end

  def neighbour_pos({xo, yo, zo, wo}) do
    for x <- -1..1, y <- -1..1, z <- -1..1, w <- -1..1, {x, y, z, w} != {0, 0, 0, 0} do
      {xo + x, yo + y, zo + z, wo + w}
    end
  end

  def step(grid) do
    # We must consider all cubes and their neighbours
    Enum.map(grid, fn {location, _} -> location end)
    |> Enum.flat_map(fn l -> neighbour_pos(l) end)
    |> Enum.uniq()
    |> Enum.map(fn location ->
      value = at(grid, location)

      if value do
        {location, (neighbours(grid, location) |> Enum.count(fn i -> i end)) in 2..3}
      else
        {location, neighbours(grid, location) |> Enum.count(fn i -> i end) == 3}
      end
    end)
    |> Enum.filter(fn {_, v} -> v end)
    |> Map.new()
  end

  @impl true
  def part1(file) do
    grid = read(file, &mapper3/2)

    Enum.reduce(1..6, grid, fn _, g -> step(g) end)
    |> Enum.count()
    |> IO.inspect()
  end

  @impl true
  def part2(file) do
    grid = read(file, &mapper4/2)

    Enum.reduce(1..6, grid, fn _, g -> step(g) end)
    |> Enum.count()
    |> IO.inspect()
  end
end

defmodule Day6 do
  @behaviour Runner.Day

  def read(file) do
    File.read!(file)
    |> String.split("\n\n")
  end

  @impl true
  def part1(file) do
    read(file)
    |> Enum.map(fn g -> String.replace(g, "\n", "") end)
    |> Enum.map(&String.graphemes/1)
    |> Enum.map(&Enum.uniq/1)
    |> Enum.map(&Enum.count/1)
    |> Enum.sum()
    |> IO.inspect()
  end

  @impl true
  def part2(file) do
    read(file)
    |> Enum.map(&String.graphemes/1)
    |> Enum.map(&Enum.frequencies/1)
    |> Enum.map(fn map ->
      everybody = Map.get(map, "\n", 0) + 1

      Map.drop(map, ["\n"])
      |> Enum.count(fn {_, v} -> v == everybody end)
    end)
    |> Enum.sum()
    |> IO.inspect()
  end
end

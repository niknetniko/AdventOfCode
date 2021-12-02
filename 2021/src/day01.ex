defmodule Day01 do
  @behaviour Day

  @impl true
  def part1(file) do
    File.read!(file)
    |> String.split("\n", trim: true)
    |> Enum.map(&String.to_integer/1)
    |> Enum.chunk_every(2, 1, :discard)
    |> Enum.count(fn [l, r] -> l < r end)
  end

  @impl true
  def part2(file) do
    File.read!(file)
    |> String.split("\n", trim: true)
    |> Enum.map(&String.to_integer/1)
    |> Enum.chunk_every(3, 1, :discard)
    |> Enum.chunk_every(2, 1, :discard)
    |> Enum.count(fn [[l, m1, m2], [m1, m2, r]] -> l < r end)
  end
end

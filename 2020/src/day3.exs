defmodule Day3 do
  @behaviour Runner.Day
  
  def read(file) do
    File.read!(file)
    |> String.split("\n")
    |> Enum.map(&String.trim/1)
  end
  
  def check_slope(lines, right, down) do
    width = lines |> Enum.at(0) |> String.length()
    1..div(Enum.count(lines), down) - 1
    |> Enum.reduce(0, fn row, acc ->
      lines 
      |> Enum.at(row * down)
      |> String.at(rem(row * right, width))
      |> (fn v -> v == "#" end).()
      |> if(do: acc + 1, else: acc)
    end)
  end
  
  @impl true
  def part1(file) do
    read(file)
    |> check_slope(3, 1)
    |> IO.inspect()
  end
  
  @impl true
  def part2(file) do
    lines = read(file)
    [{1, 1}, {3, 1}, {5, 1}, {7, 1}, {1, 2}]
    |> Enum.reduce(1, fn {right, down}, acc -> acc * check_slope(lines, right, down) end)
    |> IO.inspect()
  end
end

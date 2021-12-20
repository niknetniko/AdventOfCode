defmodule Day1 do
  @behaviour Runner.Day

  def permutations([]), do: [[]]

  def permutations(list),
    do: for(elem <- list, rest <- permutations(list -- [elem]), do: [elem | rest])

  def numbers(file) do
    File.stream!(file)
    |> Enum.map(&String.trim_trailing/1)
    |> Enum.map(&String.to_integer/1)
  end

  def combinations(one, two) do
    Enum.flat_map(one, fn e ->
      Enum.map(two, fn ee -> {e, ee} end)
    end)
  end

  def combinations(one, two, three) do
    Enum.flat_map(one, fn e ->
      Enum.flat_map(two, fn ee ->
        Enum.map(three, fn eee -> {e, ee, eee} end)
      end)
    end)
  end

  @impl true
  def part1(file) do
    values = numbers(file)

    combinations(values, values)
    |> Enum.find(fn {e, ee} -> e + ee == 2020 and e != ee end)
    |> (fn {e, ee} -> e * ee end).()
    |> IO.inspect()
  end

  @impl true
  def part2(file) do
    values = numbers(file)

    combinations(values, values, values)
    |> Enum.find(fn {e, ee, eee} -> e + ee + eee == 2020 and e != ee and ee != eee end)
    |> (fn {e, ee, eee} -> e * ee * eee end).()
    |> IO.inspect()
  end
end

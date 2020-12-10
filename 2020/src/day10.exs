defmodule Day10 do
  @behaviour Runner.Day
  
  def read(file) do
    File.stream!(file)
    |> Stream.map(&String.trim/1)
    |> Stream.map(&String.to_integer/1)
  end
  
  defp diff_list(file) do
    numbers = read(file) |> Enum.sort()
    Enum.concat([[0], numbers, [Enum.at(numbers, -1) + 3]])
    |> Stream.chunk_every(2, 1, :discard)
    |> Enum.map(fn [x, y] -> y - x end)
  end

  @impl true
  def part1(file) do
    %{1 => ones, 3 => threes} = diff_list(file) |> Enum.frequencies()
    
    IO.inspect(ones * threes)
  end
  
  defp diff_to_permutations(2), do: 2
  defp diff_to_permutations(3), do: 4
  defp diff_to_permutations(4), do: 7
  defp diff_to_permutations(_), do: 0
  
  @impl true
  def part2(file) do
    {_, _, l} = diff_list(file)
      |> Enum.reduce({0, 0, []}, fn diff, {amount, number, total} ->
        cond do
          diff == number and diff == 1 ->
            {amount + 1, number, total}
          diff == 1 -> 
            {1, 1, total}
          true ->
            {0, 0, total ++ [diff_to_permutations(amount)]}
        end
      end)
    Enum.filter(l, fn e -> e != 0 end)
    |> Enum.reduce(1, fn e, a -> e * a end)
    |> IO.inspect()
  end
end

Day10.part2("2020/src/inputs/day10.txt")
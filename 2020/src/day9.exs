defmodule Day9 do
  @behaviour Runner.Day

  def read(file) do
    File.stream!(file)
    |> Stream.map(&String.trim/1)
    |> Stream.map(&String.to_integer/1)
  end

  def combinations(one, two) do
    Stream.flat_map(one, fn e1 ->
      Stream.map(two, fn e2 -> {e1, e2} end)
    end)
  end

  defp sum_of_parts?(parts, number) do
    combinations(parts, parts)
    |> Enum.any?(fn {e1, e2} -> e1 + e2 == number end)
  end

  defp subsequences(parts) do
    # For every i >= 2, we need to take the "sliding window" of length i.
    # This should be lazy.
    2..length(Enum.to_list(parts))
    |> Stream.flat_map(&Stream.chunk_every(parts, &1, 1, :discard))
  end

  defp do_part1(numbers) do
    Stream.chunk_every(numbers, 26, 1, :discard)
    |> Stream.drop_while(fn elems ->
      number = Enum.at(elems, -1)

      Enum.slice(elems, 0..-1)
      |> sum_of_parts?(number)
    end)
    |> Stream.take(1)
    |> Enum.at(0)
    |> Enum.at(-1)
  end

  @impl true
  def part1(file), do: read(file) |> do_part1() |> IO.inspect()

  @impl true
  def part2(file) do
    numbers = read(file)
    number = do_part1(numbers)

    subsequences(numbers)
    |> Stream.filter(fn subsequence -> Enum.sum(subsequence) == number end)
    |> Stream.take(1)
    |> Enum.at(0)
    |> (fn l -> Enum.min(l) + Enum.max(l) end).()
    |> IO.inspect()
  end
end

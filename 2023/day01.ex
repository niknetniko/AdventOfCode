defmodule Day01 do
  @behaviour Day

  defp is_ascii_number(char) when 48 <= char and char <= 57, do: true
  defp is_ascii_number(_), do: false

  defp retain_ascii_numbers(iterable) do
    Enum.filter(iterable, &is_ascii_number/1)
  end

  defp combine_first_and_last([only]), do: List.to_string([only, only])
  defp combine_first_and_last(list), do: List.to_string([List.first(list), List.last(list)])

  @impl true
  def part1(file) do
    File.read!(file)
    |> String.split("\n", trim: true)
    |> Enum.map(&String.to_charlist/1)
    |> Enum.map(&retain_ascii_numbers/1)
    |> Enum.map(&combine_first_and_last/1)
    |> Enum.map(&String.to_integer/1)
    |> Enum.sum()
  end

  @impl true
  def part2(file) do
    File.read!(file)
  end
end

defmodule Day03 do
  @behaviour Day

  @impl true
  def part1(file) do
    numbers =
      File.read!(file)
      |> String.split("\n", trim: true)
      |> Enum.map(&String.to_charlist/1)

    # Get the length of the numbers
    [first | _] = numbers
    half_length = div(length(numbers), 2)

    most_common =
      Enum.map(0..(length(first) - 1), fn pos ->
        zero_count = Enum.count_until(numbers, &(Enum.at(&1, pos) == ?0), half_length + 1)
        if zero_count < half_length, do: ?1, else: ?0
      end)

    gamma = List.to_integer(most_common, 2)

    epsilon =
      Enum.map(most_common, fn bit -> if bit == ?1, do: ?0, else: ?1 end)
      |> List.to_integer(2)

    gamma * epsilon
  end

  @impl true
  def part2(file) do
    numbers =
      File.read!(file)
      |> String.split("\n", trim: true)
      |> Enum.map(&String.to_charlist/1)

    [first | _] = numbers

    [raw_oxygen] =
      Enum.reduce_while(0..(length(first) - 1), numbers, fn pos, remaining_numbers ->
        half = div(length(remaining_numbers), 2)
        zero_count = Enum.count_until(remaining_numbers, &(Enum.at(&1, pos) == ?0), half + 1)
        most_common_bit = if zero_count > half, do: ?0, else: ?1
        remainder = Enum.filter(remaining_numbers, &(Enum.at(&1, pos) == most_common_bit))

        if length(remainder) == 1, do: {:halt, remainder}, else: {:cont, remainder}
      end)

    [raw_scrubber] =
      Enum.reduce_while(0..(length(first) - 1), numbers, fn pos, remaining_numbers ->
        half = div(length(remaining_numbers), 2)
        zero_count = Enum.count_until(remaining_numbers, &(Enum.at(&1, pos) == ?0), half + 1)
        least_common_bit = if zero_count <= div(length(remaining_numbers), 2), do: ?0, else: ?1
        remainder = Enum.filter(remaining_numbers, &(Enum.at(&1, pos) == least_common_bit))

        if length(remainder) == 1, do: {:halt, remainder}, else: {:cont, remainder}
      end)

    List.to_integer(raw_oxygen, 2) * List.to_integer(raw_scrubber, 2)
  end
end

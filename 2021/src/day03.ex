defmodule Day03 do
  @behaviour Day

  use Bitwise

  defp parse_number(str) do
    String.codepoints(str)
    |> Enum.map(&String.to_integer/1)
  end

  # From https://elixirforum.com/t/how-to-manipurate-bitstring-not-binary-such-as-making-a-value-concatinating-converting-into-integer/22654/4
  defp pad_leading_zeros(bs) when is_binary(bs), do: bs
  defp pad_leading_zeros(bs) when is_bitstring(bs) do
    pad_length = 8 - rem(bit_size(bs), 8)
    <<0::size(pad_length), bs::bitstring>>
  end

  defp find_most_common(data, position) do
    data
    |> Enum.reduce([0, 0], fn data, [z, o] -> [z + bxor(1, Enum.at(data, position)), o + Enum.at(data, position)] end)
    |> Enum.with_index()
    |> Enum.max_by(fn {v, _} -> v end)
    |> then(fn {_, i} -> i end)
  end

  defp find_least_common(data, position) do
    data
    |> Enum.reduce([0, 0], fn data, [z, o] -> [z + bxor(1, Enum.at(data, position)), o + Enum.at(data, position)] end)
    |> Enum.with_index()
    |> Enum.min_by(fn {v, _} -> v end)
    |> then(fn {_, i} -> i end)
  end

  @impl true
  def part1(file) do
    number = File.read!(file)
    |> String.split("\n", trim: true)
    |> Enum.map(&parse_number/1)
    length = length(Enum.at(number, 0)) - 1

    gamma = Enum.map(0..length, fn pos -> find_most_common(number, pos) end)
            |> Enum.into(<<>>, fn b -> <<b:: 1>> end)
            |> pad_leading_zeros()
            |> :binary.decode_unsigned()
    epsilon = Enum.map(0..length, fn pos -> find_least_common(number, pos) end)
              |> Enum.into(<<>>, fn b -> <<b:: 1>> end)
              |> pad_leading_zeros()
              |> :binary.decode_unsigned()

    gamma * epsilon
  end

  def apply_command2(["forward", x], [h, d, a]), do: [h + x, d + x * a, a]
  def apply_command2(["up", x], [h, d, a]), do: [h, d, a - x]
  def apply_command2(["down", x], [h, d, a]), do: [h, d, a + x]

  @impl true
  def part2(file) do

  end
end

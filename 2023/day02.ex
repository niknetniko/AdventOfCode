defmodule Day02 do
  @behaviour Day

  defp parse_revelation(revelation) do
    String.split(revelation, ",", trim: true)
    |> Enum.map(fn amount -> String.split(amount, " ", trim: true) end)
    |> Enum.map(fn [amount, color] -> {String.to_atom(color), String.to_integer(amount)} end)
  end

  defp parse_game("Game " <> game_data) do
    [id, revelations] = String.split(game_data, ":", trim: true)

    data = String.split(revelations, ";", trim: true) |> Enum.map(&parse_revelation/1)

    {String.to_integer(id), data}
  end

  defp satisfies_constraints?({_, data}, constraints) do
    Enum.all?(data, fn revelation ->
      Enum.all?(revelation, fn {color, amount} ->
        max = Keyword.get(constraints, color, 0)
        amount <= max
      end)
    end)
  end

  defp minimum_cubes({_, data}) do
    Enum.reduce(data, [], fn revelation, acc ->
      Enum.reduce(revelation, acc, fn {colour, amount}, accc ->
        Keyword.update(accc, colour, amount, fn exiting_minimum ->
          max(exiting_minimum, amount)
        end)
      end)
    end)
  end

  defp game_power(minima) do
    Enum.map(minima, fn {_, amount} -> amount end)
    |> Enum.product()
  end

  @impl true
  def part1(file) do
    File.read!(file)
    |> String.split("\n", trim: true)
    |> Enum.map(&parse_game/1)
    |> Enum.filter(fn game -> satisfies_constraints?(game, red: 12, green: 13, blue: 14) end)
    |> Enum.map(fn {id, _} -> id end)
    |> Enum.sum()
  end

  @impl true
  def part2(file) do
    File.read!(file)
    |> String.split("\n", trim: true)
    |> Enum.map(&parse_game/1)
    |> Enum.map(&minimum_cubes/1)
    |> Enum.map(&game_power/1)
    |> Enum.sum()
  end
end

defmodule Day04 do
  @behaviour Day

  defp parse_card("Card " <> pile) do
    [id, cards] = String.split(pile, ":", trim: true)
    [winning, actual] = String.split(cards, "|", trim: true) |> Enum.map(fn l -> String.split(l, " ", trim: true) |> Enum.map(&String.to_integer/1) end)
    {String.to_integer(String.trim(id)), {winning, actual}}
  end

  defp count_points({_, {winning, actual}}) do
    Enum.reduce(actual, 0, fn number, points ->
      if number in winning do
        if points == 0, do: 1, else: points * 2
      else
        points
      end
    end)
  end

  defp get_matching({_, {winning, actual}}) do
    Enum.filter(actual, fn number -> number in winning end)
  end

  @impl true
  def part1(file) do
    File.read!(file)
    |> String.split("\n", trim: true)
    |> Enum.map(&parse_card/1)
    |> Enum.map(&count_points/1)
    |> Enum.sum()
  end

  defp resolve_scratchcard(all_cards, {id, _} = card) do
    case get_matching(card) do
      [] ->
        [id]
      matches ->
        new_cards = Enum.map(id+1..id+length(matches), fn m -> {m, all_cards[m]} end)
        winners = resolve_scratchcards(all_cards, new_cards)
        [id | winners]
    end
  end

  defp resolve_scratchcards(all_cards, cards) do
    Enum.flat_map(cards, fn c -> resolve_scratchcard(all_cards, c) end)
  end

  @impl true
  def part2(file) do
    start_cards = File.read!(file)
                |> String.split("\n", trim: true)
                |> Enum.map(&parse_card/1)
    Enum.into(start_cards, %{})
    |> resolve_scratchcards(start_cards)
    |> length()
  end
end

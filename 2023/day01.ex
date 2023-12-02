defmodule Day01 do
  @behaviour Day

  @number_words ~w[one two three four five six seven eight nine]
  @numer_literals ~w[0 1 2 3 4 5 6 7 8 9]

  defp as_number(c, _) when c in @numer_literals, do: c

  defp as_number(c, matches) do
    index = Enum.find_index(matches, fn l -> l == c end)
    Integer.to_string(index + 1)
  end

  defp member_prefix?(enumerable, prefix) do
    Enum.any?(enumerable, fn potential_match -> String.starts_with?(potential_match, prefix) end)
  end

  # Search for the first occurrence of a set of strings in a bigger string.
  # (Note: this could be a lot more efficiënt by doing charlist stuff)
  # The char is the element we are looking at, the char buffer is the
  # accumulator of the reducer.
  # It returns the match.
  defp matcher_and_combiner(char, char_buffer, potential_matches) do
    cond do
      Enum.member?(potential_matches, char_buffer <> char) ->
        # We have found match
        {:halt, char_buffer <> char}

      Enum.member?(potential_matches, char) ->
        # We have found match
        {:halt, char}

      member_prefix?(potential_matches, char_buffer <> char) ->
        # The buffer is a prefix for one of the matches, so continue.
        {:cont, char_buffer <> char}

      true ->
        # The current buffer is not a prefix, so reduce it.
        # In most cases, we can discard it, but sometimes there is overlap, so start dropping
        # chars at the front of the buffer until we have a new buffer that works.
        # In actual implementation, start at the end and prefix the accumulator until
        # we get something that no longer works.
        new_buffer =
          String.graphemes(char_buffer)
          |> Enum.reverse()
          |> Enum.reduce_while(char, fn el, acc ->
            if member_prefix?(potential_matches, el <> acc) do
              {:cont, el <> acc}
            else
              {:halt, acc}
            end
          end)

        {:cont, new_buffer}
    end
  end

  defp find_first_number(search_string, matches) do
    String.graphemes(search_string)
    |> Enum.reduce_while("", fn e, acc -> matcher_and_combiner(e, acc, matches) end)
    |> as_number(matches)
  end

  defp find_last_number(search_string, matches) do
    matches = Enum.map(matches, &String.reverse/1)

    String.reverse(search_string)
    |> String.graphemes()
    |> Enum.reduce_while("", fn e, acc -> matcher_and_combiner(e, acc, matches) end)
    |> as_number(matches)
  end

  defp combined_first_and_last_number(search_string, matches) do
    first = find_first_number(search_string, matches)
    last = find_last_number(search_string, matches)
    comb = first <> last
    comb
  end

  @impl true
  def part1(file) do
    File.read!(file)
    |> String.split("\n", trim: true)
    |> Enum.map(fn line -> combined_first_and_last_number(line, @numer_literals) end)
    |> Enum.map(&String.to_integer/1)
    |> Enum.sum()
  end

  @impl true
  def part2(file) do
    File.read!(file)
    |> String.split("\n", trim: true)
    |> Enum.map(fn line ->
      combined_first_and_last_number(line, @number_words ++ @numer_literals)
    end)
    |> Enum.map(&String.to_integer/1)
    |> Enum.sum()
  end
end

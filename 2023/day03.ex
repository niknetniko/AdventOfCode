defmodule Day03 do
  @behaviour Day

  @number_literals ~w[0 1 2 3 4 5 6 7 8 9]

  defp digit?(c) when c in @number_literals, do: true
  defp digit?(_), do: false

  defp symbol?(c) when c in @number_literals, do: false
  defp symbol?(c) when c == ".", do: false
  defp symbol?(_), do: true

  defp first({e, _}), do: e

  # Collect all positions that contain a symbol.
  defp collect_symbols(grid) do
    line_collector = fn line ->
      String.graphemes(line)
      |> Enum.with_index()
      |> Enum.filter(fn {e, _} -> symbol?(e) end)
    end

    Enum.with_index(grid)
    |> Enum.map(fn {line, index} -> {line_collector.(line), index} end)
    |> Enum.flat_map(fn {symbols, row} ->
      Enum.map(symbols, fn {symbol, col} -> {symbol, {row, col}} end)
    end)
  end

  # Collect the numbers in the grid, together with their positions.
  defp collect_numbers(grid) do
    line_collector = fn line ->
      {numbers, {buffer, positions}} =
        String.graphemes(line)
        |> Enum.with_index()
        |> Enum.reduce({[], {"", []}}, fn {char, index}, {numbers, {buffer, positions}} = acc ->
          cond do
            digit?(char) ->
              {numbers, {buffer <> char, positions ++ [index]}}

            buffer == "" ->
              acc

            true ->
              {numbers ++ [{String.to_integer(buffer), positions}], {"", []}}
          end
        end)

      if buffer == "" do
        numbers
      else
        numbers ++ [{String.to_integer(buffer), positions}]
      end
    end

    Enum.with_index(grid)
    |> Enum.map(fn {line, index} -> {line_collector.(line), index} end)
    |> Enum.flat_map(fn {numbers, row} ->
      Enum.map(numbers, fn {number, cols} ->
        {number, Enum.map(cols, fn col -> {row, col} end)}
      end)
    end)
  end

  defp all_neighbours(pos = {row, col}) do
    for dr <- -1..1 do
      for dc <- -1..1 do
        {row + dr, col + dc}
      end
    end
    |> Enum.filter(fn p -> p != pos end)
    |> List.flatten()
  end

  defp neighbours?(position, others) do
    Enum.any?(all_neighbours(position), fn pos -> pos in others end)
  end

  defp find_neighbours(position, numbers) do
    all_neighbours(position)
    |> Enum.flat_map(fn n -> Enum.filter(numbers, fn {_, poss} -> n in poss end) end)
    |> Enum.uniq_by(&first/1)
  end

  @impl true
  def part1(file) do
    grid = String.split(File.read!(file), "\n", trim: true)
    symbols = Enum.map(collect_symbols(grid), fn {_, pos} -> pos end)

    collect_numbers(grid)
    |> Enum.filter(fn {_, poss} -> Enum.any?(poss, fn pos -> neighbours?(pos, symbols) end) end)
    |> Enum.map(fn {n, _} -> n end)
    |> Enum.sum()
  end

  @impl true
  def part2(file) do
    grid = String.split(File.read!(file), "\n", trim: true)
    numbers = collect_numbers(grid)

    collect_symbols(grid)
    |> Enum.filter(fn {sym, _} -> sym == "*" end)
    |> Enum.map(fn {_, pos} -> pos end)
    |> Enum.map(fn pos -> find_neighbours(pos, numbers) end)
    |> Enum.filter(fn n -> length(n) == 2 end)
    |> Enum.map(fn [{n1, _}, {n2, _}] -> n1 * n2 end)
    |> Enum.sum()
  end
end

defmodule Day05 do
  @behaviour Day

  defp parse_numbers(line) do
    String.split(line, " ", trim: true) |> Enum.map(&String.to_integer/1)
  end

  defp collect_mappings(lines) do
    {collection, current} =
      Enum.reduce(lines, {[], []}, fn line, {collection, current} ->
        cond do
          String.ends_with?(line, ":") ->
            if current == [], do: {collection, current}, else: {collection ++ [current], []}

          true ->
            {collection, current ++ [parse_numbers(line)]}
        end
      end)

    if current == [], do: collection, else: collection ++ [current]
  end

  defp find_mapping(element, mappings) do
    case Enum.find(mappings, fn [_, source] -> element in source end) do
      nil ->
        element

      [dest, source] ->
        #        IO.inspect("Search for #{element}")
        #        IO.inspect(source, label: "  source")
        #        IO.inspect(dest, label: "  dest")
        index = element - source.first
        #        IO.inspect("Index is #{index}, this #{element} - #{source.first}")
        dest.first + index
    end
  end

  def pmap(collection, func) do
    collection
    |> Enum.map(&(Task.async(fn -> func.(&1) end)))
    |> Enum.map(&Task.await/1)
  end

  defp resolve_mapping(starters, mapping) do
    IO.inspect(starters, label: "handling")
    expanded_mapping =
      Enum.map(mapping, fn [dest_start, source_start, length] ->
        #      IO.inspect("Doing source: #{source_start}+#{length} to #{dest_start}+#{length}")
        [dest_start..(dest_start + length - 1), source_start..(source_start + length - 1)]
      end)

    pmap(starters, fn start_position ->
      {start_position, find_mapping(start_position, expanded_mapping)}
    end)
  end

  defp resolve_to_locations({seeds, mappings}) do
    Enum.reduce(mappings, seeds, fn mapping, acc ->
      resolve_mapping(acc, mapping)
      |> Enum.map(fn {_, dest} -> dest end)
    end)
  end

  defp parse_almanac(almanac) do
    ["seeds: " <> seed_lines | mapping_lines] = almanac
    {parse_numbers(seed_lines), collect_mappings(mapping_lines)}
  end

  @impl true
  def part1(file) do
    File.read!(file)
    |> String.split("\n", trim: true)
    |> parse_almanac()
    |> resolve_to_locations()
    |> Enum.min()
  end

  @impl true
  def part2(file) do
    {seeds, mappings} = File.read!(file)
    |> String.split("\n", trim: true)
    |> parse_almanac()
    # CPU goes brrrr, so too slow...
#    |> then(fn {seeds, mappings} ->
#      seeds =
#        Enum.chunk_every(seeds, 2)
#        |> Enum.flat_map(fn [start, length] -> start..(start + length - 1) end)
#
#      {seeds, mappings}
#    end)
#    |> resolve_to_locations()
#    |> Enum.min()
  end
end

defmodule Day16 do
  @behaviour Runner.Day

  defmodule Rule do
    defstruct [:name, :one, :two]
    
    def valid?(%Rule{one: a, two: b}, value) do
      Enum.member?(a, value) or Enum.member?(b, value)
    end

    def valid_ticket?(r, values) do
      Enum.all?(values, fn v -> valid?(r, v) end)
    end
  end

  defp parse("", e), do: {nil, e}
  defp parse("your ticket:", :rules), do: {nil, :your}
  defp parse(line, :rules) do
    [name, fb, fe, sb, se] = Regex.run(~r/^([a-z ]+): (\d+)-(\d+) or (\d+)-(\d+)$/, line, capture: :all_but_first)
    {%Rule{name: name, one: String.to_integer(fb)..String.to_integer(fe), two: String.to_integer(sb)..String.to_integer(se)}, :rules}
  end
  defp parse("nearby tickets:", :your), do: {nil, :nearby}
  defp parse(line, :your), do: {{:your, String.split(line, ",") |> Enum.map(&String.to_integer/1)}, :your}
  defp parse(line, :nearby), do: {{:nearby, String.split(line, ",") |> Enum.map(&String.to_integer/1)}, :nearby}
  
  defp get_key({type, _}), do: type
  defp get_key(_), do: :rules
  
  defp get_value({_, val}), do: val
  defp get_value(val), do: val
  
  def read(file) do
    %{your: [your]} = r = File.stream!(file)
    |> Stream.map(&String.trim/1)
    |> Enum.map_reduce(:rules, &parse/2)
    |> elem(0)
    |> Enum.reject(&is_nil/1)
    |> Enum.group_by(&get_key/1, &get_value/1)
    
    %{r | your: your}
  end
  
  @impl true
  def part1(file) do
    %{rules: rules, nearby: nearby} = read(file)
    
    Enum.flat_map(nearby, fn n -> n end)
    |> Enum.filter(fn value ->
      !Enum.any?(rules, fn rule -> Rule.valid?(rule, value) end)
    end)
    |> Enum.sum()
    |> IO.inspect()
  end

  defp transpose([]), do: []
  defp transpose([[] | _]), do: []
  defp transpose(m) do
    [Enum.map(m, &hd/1) | transpose(Enum.map(m, &tl/1))]
  end

  @impl true
  def part2(file) do
    %{rules: rules, nearby: nearby, your: your} = read(file)
    
    Enum.filter(nearby, fn ticket -> Enum.any?(rules, fn rule -> Rule.valid_ticket?(rule, ticket) end) end)
    |> transpose()
    |> Enum.map(fn field -> Enum.filter(rules, fn rule -> Rule.valid_ticket?(rule, field) end) end)
    |> Enum.with_index()
    |> Enum.sort_by(fn {v, _} -> length(v) end)
    |> Enum.map_reduce([], fn {v, i}, used ->
      [v] = l = Enum.reject(v, fn v -> v in used end)
      {{v, i}, used ++ l}
    end)
    |> elem(0)
    |> Enum.filter(fn {v, _} -> String.starts_with?(v.name, "departure") end)
    |> Enum.map(fn {_, i} -> Enum.at(your, i) end)
    |> Enum.reduce(1, fn x, acc -> x * acc end)
    |> IO.inspect()
  end
end

defmodule Day19 do
  @behaviour Runner.Day
  
  def read(file) do
    File.read!(file)
    |> String.split("\n\n", trim: true)
    |> parse_rules_and_messages()
  end
  
  defp parse_rules_and_messages([rules, messages]) do
    %{
      rules: parse_rules(rules),
      messages: parse_messages(messages)
    }
  end
  
  defp parse_messages(messages) do
    String.split(messages, "\n", trim: true)
    |> Enum.map(&String.graphemes/1)
  end
  
  defp parse_rules(rules) do
    String.split(rules, "\n", trim: true)
    |> Enum.map(fn line ->
      [number, rule] = String.split(line, ": ")
      {String.to_integer(number), parse_rule(rule)}
    end)
    |> Map.new()
  end
  
  defp parse_rule(rule) do
    String.split(rule, " | ")
    |> IO.inspect()
    |> Enum.map(fn
        <<"\"", literal :: binary-size(1), "\"">> -> {:literal, literal}
        list -> String.split(list, " ", trim: true) |> Enum.map(&String.to_integer/1)
      end)
  end

  defmodule Rule do
    def valid?([], [], _), do: true
    def valid?([], _, _), do: false
    def valid?(_, [], _), do: false
    
    def valid?([char | unmatched], [{:literal, expected} | remaining], rules) do
      if char == expected do
        valid?(unmatched, remaining, rules)
      else
        false
      end
    end
    
    def valid?(message, [[one, other] | remaining], rules) when is_list(one) and is_list(other) do
      valid?(message, [one | remaining], rules) or valid?(message, [other | remaining], rules)
    end

    def valid?(message, [concats | remaining], rules) when is_list(concats) do
      # Flatten rule.
      valid?(message, Enum.concat(concats, remaining), rules)
    end
    
    def valid?(message, [rule | remaining], rules) when is_integer(rule) do
      valid?(message, [Map.fetch!(rules, rule) | remaining], rules)
    end
  end

  @impl true
  def part1(file) do
    %{rules: rules, messages: messages} = read(file)
     zero = Map.fetch!(rules, 0)
     
    Enum.count(messages, fn message -> Rule.valid?(message, zero, rules) end)
    |> IO.inspect()
  end

  @impl true
  def part2(file) do
    %{rules: rules, messages: m} = read(file)

    rules = rules
      |> Map.replace!(8, parse_rule("42 | 42 8"))
      |> Map.replace!(11, parse_rule("42 31 | 42 11 31"))
    zero = Map.fetch!(rules, 0)

    Enum.count(m, fn message -> Rule.valid?(message, zero, rules) end)
    |> IO.inspect()
  end
end

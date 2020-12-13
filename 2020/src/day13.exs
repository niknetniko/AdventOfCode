defmodule Day13 do
  @behaviour Runner.Day
  
  def read(file) do
    [amount, busses] = File.read!(file) |> String.split("\n")
    amount = String.to_integer(amount)
    busses1 = String.split(busses, ",") |> Enum.filter(fn x -> x != "x" end) |> Enum.map(fn x -> String.to_integer(x) end)
    busses2 = String.split(busses, ",") |> Enum.map(fn
        "x" -> nil
        a -> String.to_integer(a)
      end)
    {amount, busses1, busses2}
  end

  @impl true
  def part1(file) do
    {amount, busses, _} = read(file)
    
    # For each minute after the arrival, check if it is a multiple
    departure = Stream.iterate(amount, fn a -> a + 1 end)
    |> Enum.find(fn am -> Enum.any?(busses, fn b -> rem(am, b) == 0 end) end)
    bus = Enum.find(busses, fn b -> rem(departure, b) == 0 end)
    
    IO.inspect(bus * (departure - amount))
  end

  defp stream_multiples(step) do
    Stream.iterate(0, fn a -> a + step end)
  end
  
  
  
  defp match?(busses, pattern, minutes) do
    #IO.inspect(Enum.max(minutes))
    mapping = Enum.map(minutes, fn minute -> Enum.filter(busses, fn b -> rem(minute, b) == 0 end) end)
    Enum.zip(pattern, mapping)
    |> Enum.all?(fn
      {nil, _} -> true
      {b, r} -> Enum.member?(r, b)
    end)
  end

  # Translated from Rosetta stone Erlang
  defmodule ChineseRemainder do
    
    def chinese_remainder({moduli, residues}) do
      mod_pi = List.foldl(moduli, 1, fn a, b -> a * b end)
      crt_moduli = Enum.map(moduli, &div(mod_pi, &1))

      case calc_inverses(crt_moduli, moduli) do
        nil ->
          nil

        inverses ->
          crt_moduli
          |> Enum.zip(Enum.zip(residues, inverses) |> Enum.map(fn {a, b} -> a * b end))
          |> Enum.map(fn {a, b} -> a * b end)
          |> Enum.sum()
          |> mod(mod_pi)
      end
    end

    def egcd(_, 0), do: {1, 0}

    def egcd(a, b) do
      {s, t} = egcd(b, rem(a, b))
      {t, s - div(a, b) * t}
    end

    defp mod_inv(a, b) do
      {x, y} = egcd(a, b)
      (a * x + b * y == 1 && x) || nil
    end

    defp mod(a, m) do
      x = rem(a, m)
      (x < 0 && x + m) || x
    end

    defp calc_inverses([], []), do: []

    defp calc_inverses([n | ns], [m | ms]) do
      case mod_inv(n, m) do
        nil -> nil
        inv -> [inv | calc_inverses(ns, ms)]
      end
    end
  end
  
  @impl true
  def part2(file) do
    {_, riding, busses} = read(file)
    
    raw = Enum.with_index(busses)
    |> Enum.filter(fn {b, i} -> !is_nil(b) end)
    |> Enum.map(fn {b, i} -> {b, -i} end)
    |> Enum.unzip()
    |> ChineseRemainder.chinese_remainder()
    |> IO.inspect()
  end
end

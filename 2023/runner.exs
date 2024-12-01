defmodule Runner do
  def run(day, part, input) do
    name = module_name(day)
    apply(:"Elixir.#{name}", part_name(part), [input])
  end

  defp module_name(day) do
    "Day#{day}"
  end

  defp part_name(part) do
    :"part#{part}"
  end

  def execute do
    [day, part, input] = System.argv()

    run(day, part, input)
    |> IO.inspect(charlists: :as_lists)
  end
end

Runner.execute()

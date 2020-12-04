defmodule Runner do
  defmodule Day do
    @doc """
    Run part 1 of the day.
    """
    @callback part1(String.t) :: none()
    
    @doc """
    Run part 2 of the day.
    """
    @callback part2(String.t) :: none()
  end
  
  def run(day, part, input) do
    name = module_name(day)
    modules = Code.require_file("2020/src/#{String.downcase(name)}.exs")
    {module, _} = Enum.find(modules, fn {m, _} -> m == :"Elixir.#{name}" end)
    apply(module, part_name(part), [input])
  end
  
  defp module_name(day) do
    number = String.to_integer(day)
    "Day#{number}"
  end
  
  defp part_name(part) do
    :"part#{part}"
  end
  
  def execute do
    [day, part, input] = System.argv()
    run(day, part, input)
  end
end

Runner.execute()

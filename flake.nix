{
  description = "AoC";

  inputs = {
    nixpkgs.url = "github:nixos/nixpkgs/nixos-unstable";
    flake-utils.url = "github:numtide/flake-utils";
    devshell = {
      url = "github:numtide/devshell";
      inputs = {
        flake-utils.follows = "flake-utils";
        nixpkgs.follows = "nixpkgs";
      };
    };
  };

  outputs = { self, nixpkgs, devshell, flake-utils }:
    flake-utils.lib.eachDefaultSystem (system:
      let
        pkgs = import nixpkgs { inherit system; overlays = [ devshell.overlay ]; };
      in
      {
        devShells = rec {
          default = site;
          site = pkgs.devshell.mkShell {
            name = "AoC";
            packages = with pkgs; [
              jdk
              elixir
              clang
              clang-tools
              gnumake
              valgrind
            ];
            devshell.startup.link.text = ''
              mkdir -p "$PRJ_DATA_DIR/mix"
              mkdir -p "$PRJ_DATA_DIR/hex"
            '';
            env = [
              {
                name = "MIX_HOME";
                eval = "$PRJ_DATA_DIR/mix";
              }
              {
                name = "HEX_HOME";
                eval = "$PRJ_DATA_DIR/hex";
              }
             {
                name = "PATH";
                prefix = "$MIX_HOME/bin";
              }
              {
                name = "PATH";
                prefix = "$HEX_HOME/bin";
              }
            ];
            commands = [
              {
                name = "idea";
                category = "editor";
                help = "Start Intellij Ultimate (system) in this project.";
                command = ''
                  idea-ultimate . >/dev/null 2>&1 &
                '';
              }
            ];
          };
        };
      }
    );
}

{
  description = "AoC";

  inputs = {
    nixpkgs.url = "github:nixos/nixpkgs/nixos-unstable";
    flake-utils.url = "github:numtide/flake-utils";
    devshell = {
      url = "github:numtide/devshell";
      inputs.nixpkgs.follows = "nixpkgs";
    };
  };

  outputs = { self, nixpkgs, devshell, flake-utils }:
    flake-utils.lib.eachDefaultSystem (system:
      let
        pkgs = import nixpkgs { inherit system; overlays = [ devshell.overlays.default ]; };
      in
      {
        devShells = rec {
          default = site;
          site = pkgs.devshell.mkShell {
            name = "AoC";
            packages = with pkgs; [
              jdk
              erlang
              elixir
#               clang
#               clang-tools
              gnumake
              valgrind
              perf-tools
              linuxPackages_latest.perf
              hyperfine
              gfortran
            ];
            devshell.startup.link.text = ''
              mkdir -p "$PRJ_DATA_DIR/mix"
              mkdir -p "$PRJ_DATA_DIR/hex"
              ln -sfn ${pkgs.erlang} "$PRJ_DATA_DIR/current/erlang"
              ln -sfn ${pkgs.elixir} "$PRJ_DATA_DIR/current/elixir"
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
          };
        };
      }
    );
}

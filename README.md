# Prowl

A grid-based predator/prey simulation written in Java. Cats hunt mice across a city grid, zombie cats roam unpredictably, and the ecosystem evolves over configurable rounds with periodic spawning.

Built as an OOP exercise exploring abstract classes, inheritance, and simulation design.

## How It Works

The simulation runs on a 2D city grid populated with three creature types:

- **Mice** — move around the grid, reproduce, and try to survive
- **Cats** — hunt the closest mouse, eat them, and die if they go too long without eating
- **Zombie Cats** — move erratically and can't be reasoned with

Every N rounds a new mouse spawns. Every M rounds a new cat spawns. The simulation runs until the specified number of rounds is complete.

## File Structure

```
prowl/
├── src/
│   ├── main/java/prowl/
│   │   ├── Simulator.java       — entry point, parses args and runs the loop
│   │   ├── City.java            — manages the grid, creature lists, and simulation steps
│   │   ├── Creature.java        — abstract base class for all creatures
│   │   ├── Cat.java             — hunts the closest mouse
│   │   ├── Mouse.java           — moves and reproduces
│   │   ├── GridPoint.java       — represents a position on the grid
│   │   └── PlotterPoint.java    — maps creature position to a colored visual point
│   └── test/java/prowl/
│       └── Tester.java          — unit tests
├── lib/
│   ├── Plotter.jar              — visualization tool (reads stdout)
│   └── junit-platform-console-standalone-1.7.0-M1.jar
├── work/                        — compiled output (gitignored)
├── docs/
│   └── uml_diagram.png          — class diagram
├── .gitignore
└── README.md
```

## Class Structure

```
Creature (Abstract)
├── Cat
└── Mouse

City          — manages the grid, creature lists, and simulation steps
GridPoint     — represents a position on the grid
Simulator     — entry point, parses args and runs the loop
```

## Prerequisites

Install Java via Homebrew if you don't have it:

```bash
brew install openjdk
sudo ln -sfn /opt/homebrew/opt/openjdk/libexec/openjdk.jdk /Library/Java/JavaVirtualMachines/openjdk.jdk
echo 'export PATH="/opt/homebrew/opt/openjdk/bin:$PATH"' >> ~/.zshrc
source ~/.zshrc
```

Verify:

```bash
java -version
javac -version
```

## Running It

### Compile

From the repo root:

```bash
javac -cp lib/junit-platform-console-standalone-1.7.0-M1.jar -d work src/main/java/prowl/*.java src/test/java/prowl/Tester.java
```

### Run without visualization

```bash
java -cp work prowl.Simulator <numMice> <numCats> <numZombieCats> <rounds> [randSeed] [--DEBUG]
```

### Run with visualization

Pipe output into the Plotter:

```bash
java -cp work prowl.Simulator <numMice> <numCats> <numZombieCats> <rounds> | java -jar lib/Plotter.jar
```

### Run tests

```bash
java -cp work:lib/junit-platform-console-standalone-1.7.0-M1.jar org.junit.runner.JUnitCore Tester
```

### Arguments

| Argument | Description |
|---|---|
| `numMice` | Number of mice to start with |
| `numCats` | Number of cats to start with |
| `numZombieCats` | Number of zombie cats to start with |
| `rounds` | Number of simulation rounds to run |
| `randSeed` | Optional. Random seed for reproducibility (default: 100) |
| `--DEBUG` | Optional. Pauses between rounds, waiting for input to continue |

### Examples

```bash
# 10 mice, 3 cats, 2 zombie cats, 500 rounds with visualization
java -cp work prowl.Simulator 10 3 2 500 | java -jar lib/Plotter.jar

# Reproducible run with seed
java -cp work prowl.Simulator 10 3 2 500 42 | java -jar lib/Plotter.jar

# Step through round by round
java -cp work prowl.Simulator 10 3 2 500 42 --DEBUG
```

## Notes

- A new mouse spawns every 100 rounds
- A new cat spawns every 25 rounds
- Zombie cats are implemented but currently disabled in `City.java`
- Compiled `.class` files go into `work/` and are excluded from the repo via `.gitignore`
- Tests 1–5 check OOP structure via file scanning; tests 6–10 validate simulation output against expected values

## Status

Completed coursework project (Fall 2023). Kept as an early example of Java OOP — abstract classes, inheritance, and simulation architecture.
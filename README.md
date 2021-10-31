# TNoodle CLI
Generate WCA scrambles on the command line!


## Installation
Just make sure to run Java 11 or higher.

## Usage
The tool itself contains detailed documentation for its use.
```bash
java -jar tnoodle-cli-VERSION.jar --help
```

Make sure to run the `puzzles` command to see the particular strings used by this tool to identify each type of puzzle.
 * This decision makes it easier to maintain this CLI tool as different puzzles and scrambling algorithms are added/removed from the official scrambler.

### Examples
#### Generating WCA scrambles
Generating a single 3x3 scramble:
```
> java -jar .\tnoodle-cli.jar scramble --puzzle three
D L' U L U2 L' U2 B U' R' F2 R L' D2 L D2 F2 L
```

Generating a single 4x4 scramble:
```
> java -jar .\tnoodle-cli.jar scramble --puzzle four
L' F B2 L F2 B2 L' F2 L2 R B2 U' B' D' L2 D' F2 B2 R2 Fw2 U Rw2 Uw2 R' U' Rw2 R D2 B2 Rw2 Fw2 L Fw' F B2 Uw' D L Uw2 Rw' D Rw' U'
```

Generating five Skewb scrambles in JSON format:
```
> java -jar .\tnoodle-cli.jar scramble --puzzle skewb --count 5 --output "skewb.json"
[
  "L B L B' L' U R B L' B L",
  "L U R B U B R' B L B R",
  "B U L' U' L R' B R' B U L'",
  "B L U B' U' B' L B' L U' R'",
  "B R U' B' U L U' L' B' U' L"
]
```
#### Creating SVG images of scrambles
```
> java -jar .\tnoodle-cli.jar draw --puzzle three --scramble "D L' U L U2 L' U2 B U' R' F2 R L' D2 L D2 F2 L" --output three.svg
```
![Image of a WCA 3x3 Scramble](docs/resources/images/three.svg)

## Build
TNoodle CLI uses Gradle, so building from source is just the standard gradle build command.
```
./gradlew build
```
The built .jar file will be in `./build/libs/tnoodle-cli-VERSION-all.jar` 

## License
This tool is licensed under the GPLv3 because its primary dependency, the official WCA `tnoodle-lib`, is licensed under the GPLv3. This means that you have full freedom to use, modify, and redistribute this tool, provided that you do so under these same license terms. See the `LICENSE.md` for the full legal details.

Note, however, that this CLI tool can be aggregated into a larger collection of programs without the whole collection being deemed a "derivative work" that must be redistributed under the terms of the GPLv3. See these Frequently Asked Questions (FAQs) from the Free Software Foundation (FSF) --the author of the GPLv3 license-- for more details:
- https://www.gnu.org/licenses/gpl-faq.html#RequireCitation
- https://www.gnu.org/licenses/gpl-faq.html#GPLOutput
- https://www.gnu.org/licenses/gpl-faq.html#WhatCaseIsOutputGPL


use fancy_regex::Regex;
use std::fs;

fn solve_with(input: &str, regex: &str) -> u64 {
    let re = Regex::new(regex).unwrap();
    input
        .split(",")
        .map(|x| {
            let mut iter = x.split("-").map(|y| y.parse::<u64>().unwrap());
            (iter.next().unwrap(), iter.next().unwrap())
        })
        .fold(0, |result, range| {
            result
                + (range.0..=range.1)
                    .filter(|x| re.is_match(&x.to_string()).unwrap())
                    .sum::<u64>()
        })
}

fn solve(input: &str) -> u64 {
    solve_with(input, "^(.+)\\1$")
}
fn solve2(input: &str) -> u64 {
    solve_with(input, "^(.+)\\1+$")
}

fn main() {
    let example_output = [1227775554u64, 4174379265u64];

    for challenge in [1, 2] {
        let solver = match challenge {
            1 => solve,
            2 => solve2,
            _ => panic!("Unknown challenge"),
        };
        println!("Challenge {}", challenge);
        for is_example in [true, false] {
            let file_path = if is_example {
                "../src/Day02_test.txt"
            } else {
                "../src/Day02.txt"
            };

            let contents = fs::read_to_string(file_path).unwrap();
            let result = solver(&contents);
            if is_example {
                if result != example_output[challenge - 1] {
                    println!(
                        "Example failed for challenge {}: got {}, expected {}",
                        challenge,
                        result,
                        example_output[challenge - 1]
                    );
                    break;
                }
            } else {
                println!("Result: {}", result);
            }
        }
    }
}

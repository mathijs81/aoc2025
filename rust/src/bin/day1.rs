use std::fs;

fn solve(input: &str) -> i32 {
    let lines: Vec<i32> = input
        .lines()
        .map(|l| l[1..].parse::<i32>().unwrap() * if l.starts_with('L') { -1 } else { 1 })
        .collect();

    let mut position = 50;
    let mut count = 0;
    for x in lines {
        position = (position + x).rem_euclid(100);
        if position == 0 {
            count += 1;
        }
    }
    count
}

fn solve2(input: &str) -> i32 {
    let lines: Vec<i32> = input
        .lines()
        .map(|l| l[1..].parse::<i32>().unwrap() * if l.starts_with('L') { -1 } else { 1 })
        .collect();

    let mut position = 50;
    let mut count = 0;
    for x in lines {
        count += x.abs() / 100;
        let adjust = x % 100;
        let old_position = position;
        position = (position + x).rem_euclid(100);
        if position == 0
            || (old_position != 0 && (old_position + adjust < 0 || old_position + adjust > 100))
        {
            count += 1;
        }
    }
    count
}

fn main() {
    let example_output = [3, 6];

    for challenge in [1, 2] {
        let solver = match challenge {
            1 => solve,
            2 => solve2,
            _ => panic!("Unknown challenge"),
        };
        println!("Challenge {}", challenge);
        for is_example in [true, false] {
            // Filename is ../src/Day01.txt or ../src/Day01_test.txt
            let file_path = if is_example {
                "../src/Day01_test.txt"
            } else {
                "../src/Day01.txt"
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

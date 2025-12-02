use std::fs;

fn solve(input: &str) -> u64 {
    let ranges: Vec<(u64, u64)> = input
        .split(",")
        .map(|x| {
            let mut iter = x.split("-").map(|y| y.parse::<u64>().unwrap());
            (iter.next().unwrap(), iter.next().unwrap())
        })
        .collect();

    let mut result = 0;
    for range in ranges {
        for candidate in range.0..=range.1 {
            let stringrep = candidate.to_string();
            if stringrep.len() % 2 == 0
                && stringrep[0..stringrep.len() / 2] == stringrep[stringrep.len() / 2..]
            {
                result += candidate;
            }
        }
    }

    result
}

fn solve2(input: &str) -> u64 {
    let ranges: Vec<(u64, u64)> = input
        .split(",")
        .map(|x| {
            let mut iter = x.split("-").map(|y| y.parse::<u64>().unwrap());
            (iter.next().unwrap(), iter.next().unwrap())
        })
        .collect();

    let mut result = 0;
    for range in ranges {
        for candidate in range.0..=range.1 {
            let stringrep = candidate.to_string();
            'outer: for repeat_len in 1..=stringrep.len() / 2 {
                if stringrep.len() % repeat_len == 0 {
                    let pattern = &stringrep[0..repeat_len];
                    for index in (repeat_len..stringrep.len()).step_by(repeat_len) {
                        if &stringrep[index..index + repeat_len] != pattern {
                            break;
                        }
                        if index + repeat_len == stringrep.len() {
                            result += candidate;
                            break 'outer;
                        }
                    }
                }
            }
        }
    }
    result
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

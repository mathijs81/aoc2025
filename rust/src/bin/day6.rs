fn solve(input: &str) -> u64 {
    let lines = input.lines().collect::<Vec<&str>>();
    let numbers = lines[..lines.len() - 1]
        .iter()
        .map(|l| {
            l.split_whitespace()
                .map(|x| x.parse::<u64>().unwrap())
                .collect::<Vec<u64>>()
        })
        .collect::<Vec<Vec<u64>>>();
    let mut result = 0;
    for (i, op) in lines.last().unwrap().split_whitespace().enumerate() {
        result += match op {
            "+" => numbers.iter().map(|n| n[i]).sum::<u64>(),
            "*" => numbers.iter().map(|n| n[i]).product::<u64>(),
            _ => panic!("Invalid operation"),
        };
    }
    result
}

fn solve2(input: &str) -> u64 {
    let lines = input.lines().collect::<Vec<&str>>();

    let cols = lines[0].len();
    let mut numbers: Vec<Option<u64>> = vec![];
    for i in 0..cols {
        let mut num = 0u64;
        let mut has_num = false;
        for y in 0..lines.len() - 1 {
            let c = lines[y].chars().nth(i).unwrap();
            if c.is_ascii_digit() {
                num = num * 10 + c.to_digit(10).unwrap() as u64;
                has_num = true;
            }
        }
        if has_num {
            numbers.push(Some(num));
        } else {
            numbers.push(None);
        }
    }

    let mut result = 0u64;
    let ops = lines.last().unwrap();
    for (i, op) in ops.chars().enumerate() {
        match op {
            '+' => {
                let mut until = i + 1;
                while until < cols && ops.chars().nth(until).unwrap() == ' ' {
                    until += 1;
                }

                result += numbers[i..until]
                    .into_iter()
                    .filter_map(|n| *n)
                    .sum::<u64>();
            }
            '*' => {
                let mut until = i + 1;
                while until < cols && ops.chars().nth(until).unwrap() == ' ' {
                    until += 1;
                }
                result += numbers[i..until]
                    .into_iter()
                    .filter_map(|n| *n)
                    .product::<u64>();
            }
            ' ' => {}
            _ => panic!("Invalid operation"),
        };
    }
    result
}

fn main() {
    let example_output = [4277556u64, 3263827u64];
    aoc::run(6, &example_output, &[solve, solve2]);
}

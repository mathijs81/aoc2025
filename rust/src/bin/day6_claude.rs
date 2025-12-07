// Asked claude to directly translate my kotlin solution to rust here:

fn solve(input: &str) -> u64 {
    let lines: Vec<&str> = input.lines().collect();
    let numbers: Vec<Vec<i64>> = lines[..lines.len() - 1]
        .iter()
        .map(|line| {
            line.split_whitespace()
                .map(|s| s.parse::<i64>().unwrap())
                .collect()
        })
        .collect();

    let ops: Vec<&str> = lines[lines.len() - 1]
        .split(' ')
        .filter(|s| !s.is_empty())
        .collect();

    ops.iter()
        .enumerate()
        .map(|(index, op)| {
            let nums: Vec<i64> = numbers.iter().map(|row| row[index]).collect();
            if *op == "+" {
                nums.iter().sum::<i64>()
            } else {
                // op == "*"
                nums.iter().product::<i64>()
            }
        })
        .sum::<i64>() as u64
}

fn solve2(input: &str) -> u64 {
    let lines: Vec<&str> = input.lines().collect();
    let field: Vec<&str> = lines[..lines.len() - 1].to_vec();
    let op_line = lines[lines.len() - 1];

    op_line
        .chars()
        .enumerate()
        .map(|(index, ch)| {
            if ch != ' ' {
                let mut end = index;
                while end < field[0].len()
                    && field
                        .iter()
                        .any(|line| line.chars().nth(end).unwrap_or(' ').is_ascii_digit())
                {
                    end += 1;
                }

                let mut nums = Vec::new();
                for i in (index..end).rev() {
                    let num_str: String = field
                        .iter()
                        .map(|line| line.chars().nth(i).unwrap_or(' '))
                        .filter(|c| c.is_ascii_digit())
                        .collect();
                    nums.push(num_str.parse::<i64>().unwrap());
                }

                if ch == '+' {
                    nums.iter().sum::<i64>()
                } else {
                    // op == "*"
                    nums.iter().product::<i64>()
                }
            } else {
                0i64
            }
        })
        .sum::<i64>() as u64
}

fn main() {
    let example_output = [4277556u64, 3263827u64];
    aoc::run(6, &example_output, &[solve, solve2]);
}

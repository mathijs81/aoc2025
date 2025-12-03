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

    aoc::run(1, &example_output, &[solve, solve2]);
}

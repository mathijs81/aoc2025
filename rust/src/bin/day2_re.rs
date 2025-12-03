use fancy_regex::Regex;

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
    aoc::run(2, &example_output, &[solve, solve2]);
}

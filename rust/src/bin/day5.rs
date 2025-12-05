use std::ops::RangeInclusive;

use itertools::Itertools;

fn solve(input: &str) -> u64 {
    let mut parts = input.split("\n\n");
    let ranges: Vec<RangeInclusive<u64>> = parts
        .next()
        .unwrap()
        .lines()
        .map(|l| l.split("-").map(|x| x.parse::<u64>().unwrap()))
        .map(|mut iter| iter.next().unwrap()..=iter.next().unwrap())
        .collect();

    let queries: Vec<u64> = parts
        .next()
        .unwrap()
        .lines()
        .map(|l| l.parse::<u64>().unwrap())
        .collect();

    queries
        .iter()
        .filter(|query| ranges.iter().any(|range| range.contains(query)))
        .count() as u64
}

fn solve2(input: &str) -> u64 {
    let mut parts = input.split("\n\n");
    let ranges: Vec<RangeInclusive<u64>> = parts
        .next()
        .unwrap()
        .lines()
        .map(|l| l.split("-").map(|x| x.parse::<u64>().unwrap()))
        .map(|mut iter| iter.next().unwrap()..=iter.next().unwrap())
        .collect();

    let changes: Vec<(u64, i32)> = ranges
        .iter()
        .flat_map(|r| [(*r.start(), 1), (r.end() + 1, -1)])
        .sorted_by_key(|r| r.0)
        .collect();
    let mut result = 0u64;
    let mut current = 0u64;
    let mut in_range = 0;
    for (pos, change) in changes {
        if in_range > 0 {
            result += pos - current;
        }
        current = pos;
        in_range += change;
    }
    result
}

fn main() {
    let example_output = [3u64, 14u64];
    aoc::run(5, &example_output, &[solve, solve2]);
}

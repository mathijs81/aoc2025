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
    aoc::run(2, &example_output, &[solve, solve2]);
}

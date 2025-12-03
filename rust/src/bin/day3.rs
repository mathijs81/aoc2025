fn solve(input: &str) -> u64 {
    input
        .lines()
        .map(|l| l.bytes().map(|b| b - b'0'))
        .map(|digits| {
            let mut max_digit = -100;
            let mut best = 0i32;
            for digit in digits.rev() {
                best = best.max(digit as i32 * 10 + max_digit);
                max_digit = max_digit.max(digit as i32);
            }
            best as u32
        })
        .sum::<u32>() as u64
}

fn solve2(input: &str) -> u64 {
    input
        .lines()
        .map(|l| l.bytes().map(|b| b - b'0'))
        .map(|digits| {
            let mut best = [-1i64; 12];
            for digit in digits.rev() {
                for j in (0..12).rev() {
                    if j != 0 && best[j - 1] < 0 {
                        continue;
                    }
                    best[j] = best[j].max(if j == 0 {
                        digit as i64
                    } else {
                        digit as i64 * 10i64.pow(j as u32) + best[j - 1]
                    })
                }
            }
            best[11] as u64
        })
        .sum::<u64>()
}

fn main() {
    let example_output = [357u64, 3121910778619u64];
    aoc::run(3, &example_output, &[solve, solve2]);
}

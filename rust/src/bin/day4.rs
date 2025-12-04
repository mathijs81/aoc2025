fn solve(input: &str) -> u32 {
    let field = input
        .lines()
        .map(|l| l.bytes().collect())
        .collect::<Vec<Vec<u8>>>();

    let ylen = field.len();
    let xlen = field[0].len();
    let mut result = 0;
    for y in 0..field.len() {
        for x in 0..field[y].len() {
            if field[y][x] == b'@' {
                let mut count = 0;
                for dx in -1..=1 {
                    for dy in -1..=1 {
                        if dx == 0 && dy == 0 {
                            continue;
                        }
                        let x2 = x as i32 + dx;
                        let y2 = y as i32 + dy;
                        if x2 >= 0
                            && x2 < xlen as i32
                            && y2 >= 0
                            && y2 < ylen as i32
                            && field[y2 as usize][x2 as usize] == b'@'
                        {
                            count += 1;
                        }
                    }
                }
                if count < 4 {
                    result += 1;
                }
            }
        }
    }
    result
}

fn solve2(input: &str) -> u32 {
    let mut field = input
        .lines()
        .map(|l| l.bytes().collect())
        .collect::<Vec<Vec<u8>>>();

    let ylen = field.len();
    let xlen = field[0].len();
    let mut result = 0;
    loop {
        let mut done = true;
        for y in 0..field.len() {
            for x in 0..field[y].len() {
                if field[y][x] == b'@' {
                    let mut count = 0;
                    for dx in -1..=1 {
                        for dy in -1..=1 {
                            if dx == 0 && dy == 0 {
                                continue;
                            }
                            let x2 = x as i32 + dx;
                            let y2 = y as i32 + dy;
                            if x2 >= 0
                                && x2 < xlen as i32
                                && y2 >= 0
                                && y2 < ylen as i32
                                && field[y2 as usize][x2 as usize] == b'@'
                            {
                                count += 1;
                            }
                        }
                    }
                    if count < 4 {
                        result += 1;
                        field[y][x] = b'.';
                        done = false;
                    }
                }
            }
        }
        if done {
            break result;
        }
    }
}

fn main() {
    let example_output = [13, 43];
    aoc::run(4, &example_output, &[solve, solve2]);
}

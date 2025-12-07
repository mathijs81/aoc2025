fn count_splits(
    x: i32,
    y: i32,
    field: &Vec<&str>,
    y_size: i32,
    x_size: i32,
    visited: &mut Vec<Vec<bool>>,
) -> u64 {
    if y >= y_size || x < 0 || x >= x_size || visited[y as usize][x as usize] {
        return 0;
    }
    visited[y as usize][x as usize] = true;
    if field[y as usize].chars().nth(x as usize).unwrap() == '^' {
        return 1
            + count_splits(x - 1, y, field, y_size, x_size, visited)
            + count_splits(x + 1, y, field, y_size, x_size, visited);
    }
    return count_splits(x, y + 1, field, y_size, x_size, visited);
}

fn count_paths(
    x: i32,
    y: i32,
    field: &Vec<&str>,
    y_size: i32,
    x_size: i32,
    counts: &mut Vec<Vec<i64>>,
) -> i64 {
    if y >= y_size || x < 0 || x >= x_size {
        return 0;
    }
    if y == y_size - 1 {
        return 1;
    }
    if counts[y as usize][x as usize] < 0 {
        if field[y as usize].chars().nth(x as usize).unwrap() == '^' {
            counts[y as usize][x as usize] =
                count_paths(x - 1, y, field, y_size, x_size, counts) + count_paths(x + 1, y, field, y_size, x_size, counts);
        } else {
            counts[y as usize][x as usize] = count_paths(x, y + 1, field, y_size, x_size, counts);
        }
    }
    return counts[y as usize][x as usize];
}

fn solve(input: &str) -> u64 {
    let field: Vec<&str> = input.lines().collect();
    let y_size = field.len() as i32;
    let x_size = field[0].len() as i32;

    let mut visited = vec![vec![false; x_size as usize]; y_size as usize];

    count_splits(
        field[0].find('S').unwrap() as i32,
        0,
        &field,
        y_size,
        x_size,
        &mut visited,
    )
}

fn solve2(input: &str) -> u64 {
    let field: Vec<&str> = input.lines().collect();
    let y_size = field.len() as i32;
    let x_size = field[0].len() as i32;

    let mut counts = vec![vec![-1i64; x_size as usize]; y_size as usize];

    count_paths(
        field[0].find('S').unwrap() as i32,
        0,
        &field,
        y_size,
        x_size,
        &mut counts,
    ) as u64
}

fn main() {
    let example_output = [21u64, 40u64];
    aoc::run(7, &example_output, &[solve, solve2]);
}

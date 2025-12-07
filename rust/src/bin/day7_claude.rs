// Asked claude to directly translate my kotlin solution to rust here:

fn solve(input: &str) -> u64 {
    let field: Vec<&str> = input.lines().collect();
    let y_size = field.len();
    let x_size = field[0].len();
    let mut visited = vec![vec![false; x_size]; y_size];

    fn splits(
        field: &[&str],
        visited: &mut Vec<Vec<bool>>,
        x: i32,
        y: i32,
        y_size: i32,
        x_size: i32,
    ) -> i32 {
        if y >= y_size || x < 0 || x >= x_size {
            return 0;
        }
        let (ux, uy) = (x as usize, y as usize);
        if visited[uy][ux] {
            return 0;
        }
        visited[uy][ux] = true;
        if field[uy].chars().nth(ux).unwrap() == '^' {
            return 1
                + splits(field, visited, x - 1, y, y_size, x_size)
                + splits(field, visited, x + 1, y, y_size, x_size);
        }
        splits(field, visited, x, y + 1, y_size, x_size)
    }

    let start_x = field[0].find('S').unwrap() as i32;
    splits(
        &field,
        &mut visited,
        start_x,
        0,
        y_size as i32,
        x_size as i32,
    ) as u64
}

fn solve2(input: &str) -> u64 {
    let field: Vec<&str> = input.lines().collect();
    let y_size = field.len();
    let x_size = field[0].len();

    let mut count = vec![vec![-1i64; x_size]; y_size];
    for x in 0..x_size {
        count[y_size - 1][x] = 1;
    }

    fn count_paths(
        field: &[&str],
        count: &mut Vec<Vec<i64>>,
        x: i32,
        y: i32,
        y_size: i32,
        x_size: i32,
    ) -> i64 {
        if y >= y_size || x < 0 || x >= x_size {
            return 0;
        }
        let (ux, uy) = (x as usize, y as usize);
        if count[uy][ux] >= 0 {
            return count[uy][ux];
        }
        if field[uy].chars().nth(ux).unwrap() == '^' {
            count[uy][ux] = count_paths(field, count, x - 1, y, y_size, x_size)
                + count_paths(field, count, x + 1, y, y_size, x_size);
        } else {
            count[uy][ux] = count_paths(field, count, x, y + 1, y_size, x_size);
        }
        count[uy][ux]
    }

    let start_x = field[0].find('S').unwrap() as i32;
    count_paths(&field, &mut count, start_x, 0, y_size as i32, x_size as i32) as u64
}

fn main() {
    let example_output = [21u64, 40u64];
    aoc::run(7, &example_output, &[solve, solve2]);
}

use std::fs;

fn solve(input: &str) -> i32 {
    let lines: Vec<Vec<&str>> = input.split("\n").
        map(|l| l.split_whitespace().collect::<Vec<&str>>()).collect();

    let mut col1: Vec<i32> = lines.iter().map(|l| l[0].parse::<i32>().unwrap()).collect();
    let mut col2: Vec<i32> = lines.iter().map(|l| l[1].parse::<i32>().unwrap()).collect();

    col1.sort();
    col2.sort();

    return col1.iter().zip(col2.iter()).map(|(a, b)| (a - b).abs()).sum()
}


fn solve2(input: &str) -> i32 {
    let lines: Vec<Vec<&str>> = input.split("\n").
        map(|l| l.split_whitespace().collect::<Vec<&str>>()).collect();

    let col1: Vec<i32> = lines.iter().map(|l| l[0].parse::<i32>().unwrap()).collect();    
    let col2: Vec<i32> = lines.iter().map(|l| l[1].parse::<i32>().unwrap()).collect();

    let freq_map = col2.iter().fold(std::collections::HashMap::new(), |mut acc, &x| {
        *acc.entry(x).or_insert(0) += 1;
        acc
    });

    return col1.iter().map(|x| { x * freq_map.get(x).unwrap_or(&0) }).sum()
}

fn main() {
    // for example in false,true:

    let example_output= 11;

    for is_example in [true, false] {
        // Filename is ../src/Day01.txt or ../src/Day01_test.txt
        let file_path = if is_example {
            "../src/Day01_test.txt"
        } else {
            "../src/Day01.txt"
        };

        let contents = fs::read_to_string(file_path).unwrap();
        let result = solve(&contents);
        if is_example {
            assert_eq!(result, example_output);
        } else {
            println!("Result: {}", result);
        }
    }


    for is_example in [true, false] {
        // Filename is ../src/Day01.txt or ../src/Day01_test.txt
        let file_path = if is_example {
            "../src/Day01_test.txt"
        } else {
            "../src/Day01.txt"
        };

        let contents = fs::read_to_string(file_path).unwrap();
        let result = solve2(&contents);
        if is_example {
            assert_eq!(result, 31);
        } else {
            println!("Result: {}", result);
        }
    }
}

use std::fs;

pub fn run<T: PartialEq + std::fmt::Display>(
    day_num: u8,
    example_output: &[T],
    solvers: &[fn(&str) -> T],
) {
    let n = example_output.len();
    assert_eq!(
        solvers.len(),
        example_output.len(),
        "Number of solvers must match number of example outputs"
    );
    for i in 0..n {
        println!("Challenge {}", i + 1);

        for is_example in [true, false] {
            let file_path = if is_example {
                format!("../src/Day{day_num:02}_test.txt")
            } else {
                format!("../src/Day{day_num:02}.txt")
            };
            let contents = fs::read_to_string(file_path).unwrap();
            let result = solvers[i](&contents);
            if is_example {
                if result != example_output[i] {
                    println!(
                        "Example failed for challenge {}: got {}, expected {}",
                        i + 1,
                        result,
                        example_output[i]
                    );
                    break;
                }
            } else {
                println!("Result: {}", result);
            }
        }
    }
}

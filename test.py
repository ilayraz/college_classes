def main():
    number = float(input("Number to find the square root off: "))
    square, runs = square_root(number)
    print(f'Found the square root of {number} is {square} in {runs} runs')
    iter = int(input("Number of iterations to find e: "))
    e = e_aprox(iter)
    print(f'approximation of e: {e}')


def square_root(number):
    upper_approximation = number / 2 + 1
    lower_approximation = 1
    approximation = (upper_approximation+lower_approximation) / 2
    square_approximation = approximation*approximation

    runs = 0
    while(abs(square_approximation - number) > 0.0001):
        runs += 1
        if square_approximation > number:
            upper_approximation = approximation
        else:
            lower_approximation = approximation
        approximation = (upper_approximation+lower_approximation) / 2
        square_approximation = approximation*approximation

    return (approximation, runs)


def factorial(number):
    result = 1
    for i in range(2, number+1):
        result *= i
    return result

def e_aprox(number):
    aprox = 0
    for i in range(number):
        aprox += 1 / factorial(i)
    return aprox


if __name__ == "__main__": main()

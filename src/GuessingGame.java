public abstract class GuessingGame<T> extends Game{
    public abstract T getGuess();
    public abstract void generateSecretCode();
}

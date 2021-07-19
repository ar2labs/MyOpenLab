package tools;

import VisualLogic.*;
import VisualLogic.variables.*;
import java.io.IOException;
import java.io.Reader;
import java.io.StreamTokenizer;

public class Scanner extends StreamTokenizer implements Expression.yyInput {
  ExternalIF element;

  public Scanner(ExternalIF element, Reader r) {
    super(r);
    this.element = element;

    resetSyntax();
    commentChar('#'); // Comments from # to end-of-line
    wordChars('0', '9'); // Parse decimal numbers as words
    wordChars('a', 'z'); // Var Names
    wordChars('A', 'Z'); // Var Names
    wordChars('.', '.'); // Decimal Point for Real
    whitespaceChars(0, ' '); // Ignore control-* and space

    eolIsSignificant(true); // need '\n'
  }

  /**
   * Moves to next input token. Consumes end of line and pretends (once) that it is end of file.
   *
   * @return false at end of file and once at each end of line.
   */
  public boolean advance() throws IOException {
    if (ttype != TT_EOF) nextToken();
    return ttype != TT_EOF && ttype != TT_EOL;
  }

  // Checks whether the string has a number from -32 ... + 32 ... (16 bit)
  public boolean isConst(String value) {
    try {
      int num = Integer.valueOf(value);
      if (num >= -32768 && num <= 32767) {
        return true;
      }
    } catch (Exception ex) {
    }
    return false;
  }

  // Checks whether the string has been defined as a variable
  public boolean isVariable(String value) {
    VSBasisIF basis = element.jGetBasis();

    if (basis != null) {
      int varDT = basis.vsGetVariableDT(value);
      if (varDT > -1) {
        return true;
      }
    }
    // oder auch nicht!
    return false;
  }

  /**
   * Determines current input, sets value to String for Int and Real.
   *
   * @return Int, Real or token's character value.
   */
  public int token() {
    switch (ttype) {
      case TT_EOL:
        System.out.println("EOL ");
      case TT_EOF:
        assert true; // Should not happen
      case TT_WORD:
        {
          value = sval;

          if (isConst(sval)) return Expression.WORD;
          if (isVariable(sval)) return Expression.Variable;
          else {
            element.jShowMessage("not a valid expresssion!");
          }

          return Expression.Variable;
        }
      default:
        value = null;
        return ttype;
    }
  }

  /** value associated with current input. */
  protected Object value;

  /**
   * produces value associated with current input.
   *
   * @return value.
   */
  public Object value() {
    return value;
  }
}

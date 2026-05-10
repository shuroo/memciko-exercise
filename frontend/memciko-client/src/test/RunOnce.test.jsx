import { fireEvent, render, screen } from "@testing-library/react";
import App from "../App";

describe("Run Once", () => {

  test("toggles run once checkbox", () => {
    render(<App />);

    const checkbox = screen.getByRole("checkbox", {
      name: /Run Once/i
    });

    fireEvent.click(checkbox);

    expect(checkbox.checked).toBe(true);
  });

});
import tkinter as tk
from tkinter import colorchooser
from tkinter import ttk
import save

def main(title):
    windowlength = 600
    windowheight = 400

    def options():
        sub_menu(windowlength, windowheight)

    window = tk.Tk()
    window.title(title)

    window.geometry(f"{windowlength}x{windowheight}")
    window.resizable(False, False)

    if save.load_part("idle") == None:
        window.config(bg="#000000")
        save.save_data("#000000", "idle")
    else:
        window.config(bg=save.load_part("idle"))

    option_button = tk.Button(window, text="Options", command=options)
    option_button.place(x=0, y=0)
    time_button = tk.Button(window, text="Times")
    time_button.place(x=0, y=25)

    window.mainloop()


def sub_menu(windowlength, windowheight):
    def open_color_picker(name, item):
        window.config(bg=save.load_part(item))
        def pick_color():
            chosen_color = colorchooser.askcolor(title=f"Choose a color for {name}")[1]
            if chosen_color:
                window.config(bg=chosen_color)
                save.save_data(chosen_color, item)

        def default():
            if item == "idle":
                save.save_data("#000000", item)
            elif item == "starting":
                save.save_data("#00ff00", item)
            elif item == "hold":
                save.save_data("#ff0000", item)
            window.config(bg=save.load_part(item))

        for widget in color_picker_frame.winfo_children():
            widget.destroy()

        color_label = tk.Label(color_picker_frame, text=f"{name} Picker")
        color_label.pack(pady=10)

        color_button = tk.Button(color_picker_frame, text=f"Pick {name}", command=pick_color)
        color_button.pack(pady=5)

        color_button = tk.Button(color_picker_frame, text=f"Default", command=default)
        color_button.pack(pady=5)

    window = tk.Tk()
    window.title("Options")
    window.geometry(f"{windowlength}x{windowheight}")
    window.resizable(False, False)
    window.config(bg=save.load_part("idle"))

    submenu_frame = tk.Frame(window, bg="gray", width=300, height=400)
    submenu_frame.pack(side="left", fill="y")

    color_picker_frame = tk.Frame(window, bg="lightgray", width=300, height=400)
    color_picker_frame.pack(side="right", fill="y")

    idle_button = tk.Button(submenu_frame, text="Idle Colour", command=lambda: open_color_picker("Idle Colour", "idle"))
    idle_button.pack(pady=10)

    hold_button = tk.Button(submenu_frame, text="Hold Colour", command=lambda: open_color_picker("Hold Colour", "hold"))
    hold_button.pack(pady=10)

    starting_button = tk.Button(submenu_frame, text="Starting Colour", command=lambda: open_color_picker("Starting Colour", "starting"))
    starting_button.pack(pady=10)

    window.mainloop()

main("Timer")
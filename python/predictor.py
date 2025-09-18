import pandas as pd
from sklearn.linear_model import LogisticRegression
import numpy as np


df = pd.read_csv("../data/expenses.csv")


df["week"] = (df.index // 5) + 1
weekly = df.groupby("week")["amount"].sum().reset_index()


avg_spend = weekly["amount"].mean()
weekly["label"] = (weekly["amount"] > avg_spend).astype(int)


if len(weekly) > 1:
    X = weekly[["week"]].values
    y = weekly["label"].values

    model = LogisticRegression() # classification
    model.fit(X, y)

    next_week = np.array([[weekly["week"].max() + 1]])
    prediction = model.predict(next_week)[0]

    result = "High" if prediction == 1 else "Low"
else:
    result = "Not enough data"

with open("../data/prediction.txt", "w") as f:
    f.write(result)

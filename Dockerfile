FROM hirscheran/scala-mill

COPY . .

RUN apt-get update -y

# Download data using python
RUN apt-get install -y python3
RUN wget https://raw.githubusercontent.com/julianmichael/qasrl/master/scripts/download_data.py
RUN printf '1\n\nq\n' | python3 download_data.py

# Compile everything
RUN mill qasrl-state-machine-example.compile

# Run
ENTRYPOINT ["mill", "-i", "qasrl-state-machine-example.run"]
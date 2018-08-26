import java.util.*;
import java.io.*;
import javafx.util.Pair;

// write your matric number here: A0154907Y
// write your name here: Cho Chih Tun
// write list of collaborators here:
// year 2018 hash code: tPW3cEr39msnZUTL2L5J (do NOT delete this line)

class EmergencyRoom {
  // if needed, declare a private data structure here that
  // is accessible to all methods in this class
  private ArrayList<Pair<String, Integer>> patientList;
  private int BinaryHeapSize;

  public EmergencyRoom() {
    // Write necessary code during construction
    //
    // write your answer here
    patientList = new ArrayList<>();
    Pair<String, Integer> dummy = new Pair<>("DUMMY", 0);
    patientList.add(dummy);
    BinaryHeapSize = 0;
  }

  int parent(int i) { return i>>1; }  // shortcut for i/2, round down

  int left(int i) { return i<<1; } // shortcut for 2*i

  int right(int i) { return (i<<1) + 1; } // shortcut for 2*i + 1

  void shiftUp(int i) {
    while (i > 1 && patientList.get(parent(i)).getValue() < patientList.get(i).getValue()) {
      // Swap parent and child node
      Pair<String, Integer> temp = patientList.get(i);
      patientList.set(i, patientList.get(parent(i)));
      patientList.set(parent(i), temp);
      i = parent(i);
    }
  }

  void shiftDown(int i) {
    while (i <= BinaryHeapSize) {
      int maxV = patientList.get(i).getValue(), max_id = i;
      if (left(i) <= BinaryHeapSize && maxV < patientList.get(left(i)).getValue()) { // compare value of this node with its left subtree, if possible
        maxV = patientList.get(left(i)).getValue();
        max_id = left(i);
      }
      if (right(i) <= BinaryHeapSize && maxV < patientList.get(right(i)).getValue()) { // now compare with its right subtree, if possible
        maxV = patientList.get(right(i)).getValue();
        max_id = right(i);
      }

      if (max_id != i) {
        Pair<String, Integer> temp = patientList.get(i);
        patientList.set(i, patientList.get(max_id));
        patientList.set(max_id, temp);
        i = max_id;
      } else
        break;
    }
  }

  void ArriveAtHospital(String patientName, int emergencyLvl) {
    // You have to insert the information (patientName, emergencyLvl)
    // into your chosen data structure
    //
    // write your answer here
    // Creates a new patient
    Pair<String, Integer> newPatient = new Pair<>(patientName, emergencyLvl);
    
    BinaryHeapSize++; // Update total number of patients
    if (BinaryHeapSize >= patientList.size()) {
      patientList.add(newPatient);
    } else {
      patientList.set(BinaryHeapSize, newPatient);
    }

    // Fix any violation to max heap property
    shiftUp(BinaryHeapSize);
  }

  void UpdateEmergencyLvl(String patientName, int incEmergencyLvl) {
    // You have to update the emergencyLvl of patientName to
    // emergencyLvl += incEmergencyLvl
    // and modify your chosen data structure (if needed)
    //
    // write your answer here
    // Searches for the patient
    for (int i = 0; i < BinaryHeapSize; i++) {
      if (patientList.get(i).getKey().equals(patientName)) {
        Pair<String, Integer> patient = patientList.get(i); // patient to be updated
        int newEmergencyLvl = patient.getValue() + incEmergencyLvl;
        Pair<String, Integer> updatedPatient = new Pair<>(patient.getKey(), newEmergencyLvl);

        // Updates the patient in the patientList
        patientList.set(i, updatedPatient);

        // Fixes any violation to max heap property
        shiftUp(i);
        break; // Exits loop
      }
    }


  }

  void Treat(String patientName) {
    // This patientName is treated by the doctor
    // remove him/her from your chosen data structure
    //
    // write your answer here
    for (int i = 0; i < BinaryHeapSize; i++) {
      if (patientList.get(i).getKey().equals(patientName)) {
        // Creates zero priority patient to replace the treated patient
        Pair<String, Integer> patient = new Pair<>("REMOVING", 0);
        patientList.set(i, patient);
        shiftDown(i);
        patientList.remove(patient);
      }
    }
    BinaryHeapSize--;
  }

  String Query() {
    String ans = "The emergency suite is empty";

    // You have to report the name of the patient that the doctor
    // has to give the most attention to currently. If there is no more patient to
    // be taken care of, return a String "The emergency suite is empty"
    //
    // write your answer here
    if (BinaryHeapSize > 0) {
      ans = patientList.get(1).getKey();
    }

    return ans;
  }

  void run() throws Exception {
    // do not alter this method

    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    PrintWriter pr = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
    int numCMD = Integer.parseInt(br.readLine()); // note that numCMD is >= N
    while (numCMD-- > 0) {
      StringTokenizer st = new StringTokenizer(br.readLine());
      int command = Integer.parseInt(st.nextToken());
      switch (command) {
        case 0: ArriveAtHospital(st.nextToken(), Integer.parseInt(st.nextToken())); break;
        case 1: UpdateEmergencyLvl(st.nextToken(), Integer.parseInt(st.nextToken())); break;
        case 2: Treat(st.nextToken()); break;
        case 3: pr.println(Query()); break;
      }
    }
    pr.close();
  }

  public static void main(String[] args) throws Exception {
    // do not alter this method
    EmergencyRoom ps1 = new EmergencyRoom();
    ps1.run();
  }
}
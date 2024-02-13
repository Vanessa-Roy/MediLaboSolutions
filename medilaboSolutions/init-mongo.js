
db.createUser({
  user: process.env.MONGO_INITDB_USER,
  pwd: process.env.MONGO_INITDB_ROOT_PASSWORD,
  roles: [{
    role: 'readWrite',
    db: process.env.MONGO_INITDB_DATABASE
  }]
})

db.createCollection('notes');

db.notes.insertMany([
 {
    patientId: 1,
    date: "2024-01-29",
    content: "Patient reports 'feeling very good' Weight at or below recommended weight"
 },
 {
    patientId: 2,
    date: "2023-01-29",
    content: "The patient states that he feels a lot of stress at work. He also complains that his hearing has been abnormal lately."
 },
 {
    patientId: 2,
    date: "2024-01-15",
    content: "The patient reports having had a reaction to medications over the past 3 months. He also notes that his hearing continues to be abnormal"
 },
 {
    patientId: 3,
    date: "2023-01-29",
    content: "The patient states that he has recently started smoking"
 },
 {
    patientId: 3,
    date: "2024-01-15",
    content: "Patient states he is a smoker and quit smoking last year He also complains of abnormal respiratory apnea attacks Laboratory tests indicating elevated LDL cholesterol"
 },
 {
    patientId: 4,
    date: "2023-01-15",
    content: "Patient reports that it has become difficult for him to climb stairs He also complains of shortness of breath Laboratory tests indicating that antibodies are elevated Reaction to medications"
 },
 {
    patientId: 4,
    date: "2023-01-29",
    content: "Patient reports back pain when sitting for a long time"
 },
 {
    patientId: 4,
    date: "2024-01-15",
    content: "Patient reports having recently started smoking Hemoglobin A1C above recommended level"
 },
 {
    patientId: 4,
    date: "2024-01-29",
    content: "Height, Weight, Cholesterol, Dizziness and Reaction"
 },
]);


package Lucas.integrador;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;


class QuestionHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DB_NAME = "TQuiz.db";


    // Se você quiser adicionar mais perguntas ou atualizar os valores da tabela.
    private static final int DB_VERSION = 3;
    //Table name
    private static final String TABLE_NAME = "TQ";
    //Id of question
    private static final String UID = "_UID";
    //Question
    private static final String QUESTION = "QUESTION";
    //Option A
    private static final String OPTA = "OPTA";
    //Option B
    private static final String OPTB = "OPTB";
    //Option C
    private static final String OPTC = "OPTC";
    //Option D
    private static final String OPTD = "OPTD";
    //Answer
    private static final String ANSWER = "ANSWER";
    //So basically we are now creating table with first column-id , sec column-question , third column -option A, fourth column -option B , Fifth column -option C , sixth column -option D , seventh column - answer(i.e ans of  question)
    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ( " + UID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + QUESTION + " VARCHAR(255), " + OPTA + " VARCHAR(255), " + OPTB + " VARCHAR(255), " + OPTC + " VARCHAR(255), " + OPTD + " VARCHAR(255), " + ANSWER + " VARCHAR(255));";
    //Drop table query
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    QuestionHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //OnCreate is called only once
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //OnUpgrade é chamado sempre que atualizamos ou incrementamos nossa versão do banco de dados.
        sqLiteDatabase.execSQL(DROP_TABLE);
        onCreate(sqLiteDatabase);
    }

    void allQuestion() {
        ArrayList<Question> arraylist = new ArrayList<>();

        arraylist.add(new Question("Estou dirigindo e recebo uma notificação no celular, eu:",  "Pego o celular mesmo assim, e vejo a notificação correndo riscos de acidentes","Ignoro a notificação, pois não quero causar acidentes","Dou uma rápida olhada para não causar acidentes", "Verifico se tem fiscalização e olho normalmente o celular", "Ignoro a notificação, pois não quero causar acidentes"));

        arraylist.add(new Question("O que o condutor deve fazer quando estiver em uma via não iluminada?", "usar sempre a luz alta", "Usar luz alta, exceto ao cruzar com outro veículo ou ao seguí-lo", "Trafegar com o pisca alerta ligado", "Parar o veículo imediatamente e esperar até que a luz do dia ilumine a via", "Usar luz alta, exceto ao cruzar com outro veículo ou ao seguí-lo"));

        arraylist.add(new Question("Qual das opções a seguir diz um uso correto da buzina de um veículo?", "Para fazer as advertências necessárias a fim de evitar acidentes", "Para pedir ultrapassagem a um outro veículo", "Para pedir ao porteiro que abra a porta da garagem", "Para agradecer outro condutor por ter te dado uma passagem", "Para fazer as advertências necessárias a fim de evitar acidentes"));

        arraylist.add(new Question("Fui em um happy hour e bebi uma cervejinha. Para voltar para casa eu:",  "Vou dirigir, pois não bebi muito.", "Não devia, mas dirijo mesmo assim.", "Dormirei na Rua, pois estou muito mal para chegar no carro.","Volto de Carona, segurança em primeiro lugar" ,"Volto de Carona, segurança em primeiro lugar"));

        arraylist.add(new Question("Quando o Semáforo está amarelo eu:", "Depende, se o motorista Próximo passar, eu passo.", "Reduzo para parar, não quero correr riscos", "Acelero para passar mesmo, sabendo que não dá tempo.", "Acelero e passo buzinando", "Reduzo para parar, não quero correr riscos"));

        arraylist.add(new Question("Preciso chegar rápido ao meu destino, mas a via é de 80 quilômetros por hora, eu:", "Ando no limite perto dos radares.","Passo do Limite pois sou bom motorista" , "Respeito o Limite, mesmo que me atrase", "Vou pelo acostamento, pois não tem transito", "Respeito o Limite, mesmo que me atrase"));

        arraylist.add(new Question("O condutor deve ficar atento aos sinais do veiculo da frente, como luz de freio e indicador de direcao (ou seta), pois eles refletem:", "a serenidade do motorista","o itinerario que o motorista vai seguir" , "a habilidade do motorista", "o que o motorista pretende fazer", "o que o motorista pretende fazer"));

        arraylist.add(new Question("Estamos em constante inter-relacionamento no transito. Nessa situacao:", "devemos agir com responsabilidade e respeito as normas e as pessoas","temos a permissao para dar vazao a agressividade" , "estamos demonstrando sempre exemplares atitudes de civilidade, uma vez que ha poucos acidentes", "somos capazes de compreender perfeitamente os erros dos outros", "devemos agir com responsabilidade e respeito as normas e as pessoas"));

        arraylist.add(new Question("A solidariedade e outros valores morais e eticos sao:", "importantes para o convivio social, como tambem para o transito","necessarios para o transito, mas pouco importante socialmente" , "desnecessarios para a populacao no cotidiano", "desnecessarios serem repassados entre pais e filhos", "importantes para o convivio social, como tambem para o transito"));
        this.addAllQuestions(arraylist);

    }

        //Metodo que adiciona as perguntas no banco.
    private void addAllQuestions(ArrayList<Question> allQuestions) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            for (Question question : allQuestions) {
                values.put(QUESTION, question.getQuestion());
                values.put(OPTA, question.getOptA());
                values.put(OPTB, question.getOptB());
                values.put(OPTC, question.getOptC());
                values.put(OPTD, question.getOptD());
                values.put(ANSWER, question.getAnswer());
                db.insert(TABLE_NAME, null, values);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    //Lista as questões.
    List<Question> getAllOfTheQuestions() {

        List<Question> questionsList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        String coloumn[] = {UID, QUESTION, OPTA, OPTB, OPTC, OPTD, ANSWER};
        Cursor cursor = db.query(TABLE_NAME, coloumn, null, null, null, null, null);


        while (cursor.moveToNext()) {
            Question question = new Question();
            question.setId(cursor.getInt(0));
            question.setQuestion(cursor.getString(1));
            question.setOptA(cursor.getString(2));
            question.setOptB(cursor.getString(3));
            question.setOptC(cursor.getString(4));
            question.setOptD(cursor.getString(5));
            question.setAnswer(cursor.getString(6));
            questionsList.add(question);
        }

        db.setTransactionSuccessful();
        db.endTransaction();
        cursor.close();
        db.close();
        return questionsList;
    }
}

package ar.jornadas.data;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\'\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u00a7@\u00a2\u0006\u0002\u0010\u0007J\u000e\u0010\b\u001a\u00020\u0004H\u00a7@\u00a2\u0006\u0002\u0010\tJ\u000e\u0010\n\u001a\u00020\u000bH\u00a7@\u00a2\u0006\u0002\u0010\tJ\u0016\u0010\f\u001a\u00020\u00042\u0006\u0010\r\u001a\u00020\u000eH\u00a7@\u00a2\u0006\u0002\u0010\u000fJ\u0014\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00060\u0011H\u00a7@\u00a2\u0006\u0002\u0010\tJ\u001c\u0010\u0012\u001a\u00020\u00042\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00140\u0011H\u00a7@\u00a2\u0006\u0002\u0010\u0015J\u0014\u0010\u0016\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00140\u00110\u0017H\'J\u001c\u0010\u0018\u001a\u00020\u00042\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00140\u0011H\u0097@\u00a2\u0006\u0002\u0010\u0015\u00a8\u0006\u0019"}, d2 = {"Lar/jornadas/data/CarteleraDao;", "", "()V", "alternar", "", "id", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "borrarTodo", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "contarEdiciones", "", "guardarEdicion", "edicion", "Lar/jornadas/data/Edicion;", "(Lar/jornadas/data/Edicion;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "idsFavoritas", "", "insertar", "fichas", "Lar/jornadas/data/Charla;", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "observar", "Lkotlinx/coroutines/flow/Flow;", "reemplazarPrograma", "app_debug"})
@androidx.room.Dao()
public abstract class CarteleraDao {
    
    public CarteleraDao() {
        super();
    }
    
    @androidx.room.Query(value = "SELECT * FROM charlas ORDER BY dia, hora, sala")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<ar.jornadas.data.Charla>> observar();
    
    @androidx.room.Insert()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertar(@org.jetbrains.annotations.NotNull()
    java.util.List<ar.jornadas.data.Charla> fichas, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT id FROM charlas WHERE favorita = 1")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object idsFavoritas(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<java.lang.String>> $completion);
    
    @androidx.room.Query(value = "DELETE FROM charlas")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object borrarTodo(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "UPDATE charlas SET favorita = NOT favorita WHERE id = :id")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object alternar(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object guardarEdicion(@org.jetbrains.annotations.NotNull()
    ar.jornadas.data.Edicion edicion, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT COUNT(*) FROM edicion")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object contarEdiciones(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion);
    
    @androidx.room.Transaction()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object reemplazarPrograma(@org.jetbrains.annotations.NotNull()
    java.util.List<ar.jornadas.data.Charla> fichas, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
}
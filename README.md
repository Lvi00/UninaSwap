# UninaSwap

**UninaSwap** è una piattaforma Java per la compravendita, lo scambio e il regalo di oggetti tra studenti universitari. Permette agli utenti di pubblicare annunci, inviare offerte e gestire le transazioni in modo semplice e sicuro.

---

## Caratteristiche principali

- **Gestione Annunci**: Gli utenti possono creare annunci specificando:
  - Descrizione dell’oggetto
  - Categoria (es. libri di testo, materiale informatico, abbigliamento, strumenti musicali)
  - Tipologia dell’annuncio (vendita, scambio, regalo)
  - Prezzo richiesto (se pertinente)
  - Modalità di consegna (sede universitaria e fascia oraria preferita)

- **Offerte**:
  - **Vendita**: conferma del prezzo o proposta economica inferiore
  - **Scambio**: accettazione degli oggetti proposti o proposta di oggetti alternativi
  - **Regalo**: invio di una richiesta con messaggio motivazionale
  - Possibilità di modificare o ritirare offerte non ancora valutate
  - Storico delle offerte con stato (in attesa, accettata, rifiutata)

- **Autenticazione utenti**: accesso tramite username e password.

- **Filtri annunci**: visualizzazione di annunci attivi filtrati per categoria e tipologia.

- **Reportistica**:
  - Statistiche sul numero di offerte inviate e accettate per tipologia
  - Per gli annunci di vendita accettati: valore medio, minimo e massimo in euro
  - Grafici dei dati generati con una libreria come JFreeChart (http://www.jfree.org/jfreechart/) o con JavaFX

---

## Requisiti

- Java 11 o superiore
- Libreria JFreeChart
- Database (opzionale, a seconda dell’implementazione)

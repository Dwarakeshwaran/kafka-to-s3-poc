/**
 * Autogenerated by Avro
 *
 * DO NOT EDIT DIRECTLY
 */
package dwaki.localkafkaconsumer.avro.model;

import org.apache.avro.generic.GenericArray;
import org.apache.avro.specific.SpecificData;
import org.apache.avro.util.Utf8;
import org.apache.avro.message.BinaryMessageEncoder;
import org.apache.avro.message.BinaryMessageDecoder;
import org.apache.avro.message.SchemaStore;

@org.apache.avro.specific.AvroGenerated
public class Anime extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = 5647469510233691395L;


  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"Anime\",\"namespace\":\"dwaki.localkafkaconsumer.avro.model\",\"fields\":[{\"name\":\"animeName\",\"type\":\"string\"},{\"name\":\"year\",\"type\":\"int\"},{\"name\":\"rating\",\"type\":\"int\"}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }

  private static final SpecificData MODEL$ = new SpecificData();

  private static final BinaryMessageEncoder<Anime> ENCODER =
      new BinaryMessageEncoder<Anime>(MODEL$, SCHEMA$);

  private static final BinaryMessageDecoder<Anime> DECODER =
      new BinaryMessageDecoder<Anime>(MODEL$, SCHEMA$);

  /**
   * Return the BinaryMessageEncoder instance used by this class.
   * @return the message encoder used by this class
   */
  public static BinaryMessageEncoder<Anime> getEncoder() {
    return ENCODER;
  }

  /**
   * Return the BinaryMessageDecoder instance used by this class.
   * @return the message decoder used by this class
   */
  public static BinaryMessageDecoder<Anime> getDecoder() {
    return DECODER;
  }

  /**
   * Create a new BinaryMessageDecoder instance for this class that uses the specified {@link SchemaStore}.
   * @param resolver a {@link SchemaStore} used to find schemas by fingerprint
   * @return a BinaryMessageDecoder instance for this class backed by the given SchemaStore
   */
  public static BinaryMessageDecoder<Anime> createDecoder(SchemaStore resolver) {
    return new BinaryMessageDecoder<Anime>(MODEL$, SCHEMA$, resolver);
  }

  /**
   * Serializes this Anime to a ByteBuffer.
   * @return a buffer holding the serialized data for this instance
   * @throws java.io.IOException if this instance could not be serialized
   */
  public java.nio.ByteBuffer toByteBuffer() throws java.io.IOException {
    return ENCODER.encode(this);
  }

  /**
   * Deserializes a Anime from a ByteBuffer.
   * @param b a byte buffer holding serialized data for an instance of this class
   * @return a Anime instance decoded from the given buffer
   * @throws java.io.IOException if the given bytes could not be deserialized into an instance of this class
   */
  public static Anime fromByteBuffer(
      java.nio.ByteBuffer b) throws java.io.IOException {
    return DECODER.decode(b);
  }

  private java.lang.CharSequence animeName;
  private int year;
  private int rating;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public Anime() {}

  /**
   * All-args constructor.
   * @param animeName The new value for animeName
   * @param year The new value for year
   * @param rating The new value for rating
   */
  public Anime(java.lang.CharSequence animeName, java.lang.Integer year, java.lang.Integer rating) {
    this.animeName = animeName;
    this.year = year;
    this.rating = rating;
  }

  public org.apache.avro.specific.SpecificData getSpecificData() { return MODEL$; }
  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call.
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return animeName;
    case 1: return year;
    case 2: return rating;
    default: throw new IndexOutOfBoundsException("Invalid index: " + field$);
    }
  }

  // Used by DatumReader.  Applications should not call.
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: animeName = (java.lang.CharSequence)value$; break;
    case 1: year = (java.lang.Integer)value$; break;
    case 2: rating = (java.lang.Integer)value$; break;
    default: throw new IndexOutOfBoundsException("Invalid index: " + field$);
    }
  }

  /**
   * Gets the value of the 'animeName' field.
   * @return The value of the 'animeName' field.
   */
  public java.lang.CharSequence getAnimeName() {
    return animeName;
  }


  /**
   * Sets the value of the 'animeName' field.
   * @param value the value to set.
   */
  public void setAnimeName(java.lang.CharSequence value) {
    this.animeName = value;
  }

  /**
   * Gets the value of the 'year' field.
   * @return The value of the 'year' field.
   */
  public int getYear() {
    return year;
  }


  /**
   * Sets the value of the 'year' field.
   * @param value the value to set.
   */
  public void setYear(int value) {
    this.year = value;
  }

  /**
   * Gets the value of the 'rating' field.
   * @return The value of the 'rating' field.
   */
  public int getRating() {
    return rating;
  }


  /**
   * Sets the value of the 'rating' field.
   * @param value the value to set.
   */
  public void setRating(int value) {
    this.rating = value;
  }

  /**
   * Creates a new Anime RecordBuilder.
   * @return A new Anime RecordBuilder
   */
  public static dwaki.localkafkaconsumer.avro.model.Anime.Builder newBuilder() {
    return new dwaki.localkafkaconsumer.avro.model.Anime.Builder();
  }

  /**
   * Creates a new Anime RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new Anime RecordBuilder
   */
  public static dwaki.localkafkaconsumer.avro.model.Anime.Builder newBuilder(dwaki.localkafkaconsumer.avro.model.Anime.Builder other) {
    if (other == null) {
      return new dwaki.localkafkaconsumer.avro.model.Anime.Builder();
    } else {
      return new dwaki.localkafkaconsumer.avro.model.Anime.Builder(other);
    }
  }

  /**
   * Creates a new Anime RecordBuilder by copying an existing Anime instance.
   * @param other The existing instance to copy.
   * @return A new Anime RecordBuilder
   */
  public static dwaki.localkafkaconsumer.avro.model.Anime.Builder newBuilder(dwaki.localkafkaconsumer.avro.model.Anime other) {
    if (other == null) {
      return new dwaki.localkafkaconsumer.avro.model.Anime.Builder();
    } else {
      return new dwaki.localkafkaconsumer.avro.model.Anime.Builder(other);
    }
  }

  /**
   * RecordBuilder for Anime instances.
   */
  @org.apache.avro.specific.AvroGenerated
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<Anime>
    implements org.apache.avro.data.RecordBuilder<Anime> {

    private java.lang.CharSequence animeName;
    private int year;
    private int rating;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$, MODEL$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(dwaki.localkafkaconsumer.avro.model.Anime.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.animeName)) {
        this.animeName = data().deepCopy(fields()[0].schema(), other.animeName);
        fieldSetFlags()[0] = other.fieldSetFlags()[0];
      }
      if (isValidValue(fields()[1], other.year)) {
        this.year = data().deepCopy(fields()[1].schema(), other.year);
        fieldSetFlags()[1] = other.fieldSetFlags()[1];
      }
      if (isValidValue(fields()[2], other.rating)) {
        this.rating = data().deepCopy(fields()[2].schema(), other.rating);
        fieldSetFlags()[2] = other.fieldSetFlags()[2];
      }
    }

    /**
     * Creates a Builder by copying an existing Anime instance
     * @param other The existing instance to copy.
     */
    private Builder(dwaki.localkafkaconsumer.avro.model.Anime other) {
      super(SCHEMA$, MODEL$);
      if (isValidValue(fields()[0], other.animeName)) {
        this.animeName = data().deepCopy(fields()[0].schema(), other.animeName);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.year)) {
        this.year = data().deepCopy(fields()[1].schema(), other.year);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.rating)) {
        this.rating = data().deepCopy(fields()[2].schema(), other.rating);
        fieldSetFlags()[2] = true;
      }
    }

    /**
      * Gets the value of the 'animeName' field.
      * @return The value.
      */
    public java.lang.CharSequence getAnimeName() {
      return animeName;
    }


    /**
      * Sets the value of the 'animeName' field.
      * @param value The value of 'animeName'.
      * @return This builder.
      */
    public dwaki.localkafkaconsumer.avro.model.Anime.Builder setAnimeName(java.lang.CharSequence value) {
      validate(fields()[0], value);
      this.animeName = value;
      fieldSetFlags()[0] = true;
      return this;
    }

    /**
      * Checks whether the 'animeName' field has been set.
      * @return True if the 'animeName' field has been set, false otherwise.
      */
    public boolean hasAnimeName() {
      return fieldSetFlags()[0];
    }


    /**
      * Clears the value of the 'animeName' field.
      * @return This builder.
      */
    public dwaki.localkafkaconsumer.avro.model.Anime.Builder clearAnimeName() {
      animeName = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /**
      * Gets the value of the 'year' field.
      * @return The value.
      */
    public int getYear() {
      return year;
    }


    /**
      * Sets the value of the 'year' field.
      * @param value The value of 'year'.
      * @return This builder.
      */
    public dwaki.localkafkaconsumer.avro.model.Anime.Builder setYear(int value) {
      validate(fields()[1], value);
      this.year = value;
      fieldSetFlags()[1] = true;
      return this;
    }

    /**
      * Checks whether the 'year' field has been set.
      * @return True if the 'year' field has been set, false otherwise.
      */
    public boolean hasYear() {
      return fieldSetFlags()[1];
    }


    /**
      * Clears the value of the 'year' field.
      * @return This builder.
      */
    public dwaki.localkafkaconsumer.avro.model.Anime.Builder clearYear() {
      fieldSetFlags()[1] = false;
      return this;
    }

    /**
      * Gets the value of the 'rating' field.
      * @return The value.
      */
    public int getRating() {
      return rating;
    }


    /**
      * Sets the value of the 'rating' field.
      * @param value The value of 'rating'.
      * @return This builder.
      */
    public dwaki.localkafkaconsumer.avro.model.Anime.Builder setRating(int value) {
      validate(fields()[2], value);
      this.rating = value;
      fieldSetFlags()[2] = true;
      return this;
    }

    /**
      * Checks whether the 'rating' field has been set.
      * @return True if the 'rating' field has been set, false otherwise.
      */
    public boolean hasRating() {
      return fieldSetFlags()[2];
    }


    /**
      * Clears the value of the 'rating' field.
      * @return This builder.
      */
    public dwaki.localkafkaconsumer.avro.model.Anime.Builder clearRating() {
      fieldSetFlags()[2] = false;
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Anime build() {
      try {
        Anime record = new Anime();
        record.animeName = fieldSetFlags()[0] ? this.animeName : (java.lang.CharSequence) defaultValue(fields()[0]);
        record.year = fieldSetFlags()[1] ? this.year : (java.lang.Integer) defaultValue(fields()[1]);
        record.rating = fieldSetFlags()[2] ? this.rating : (java.lang.Integer) defaultValue(fields()[2]);
        return record;
      } catch (org.apache.avro.AvroMissingFieldException e) {
        throw e;
      } catch (java.lang.Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumWriter<Anime>
    WRITER$ = (org.apache.avro.io.DatumWriter<Anime>)MODEL$.createDatumWriter(SCHEMA$);

  @Override public void writeExternal(java.io.ObjectOutput out)
    throws java.io.IOException {
    WRITER$.write(this, SpecificData.getEncoder(out));
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumReader<Anime>
    READER$ = (org.apache.avro.io.DatumReader<Anime>)MODEL$.createDatumReader(SCHEMA$);

  @Override public void readExternal(java.io.ObjectInput in)
    throws java.io.IOException {
    READER$.read(this, SpecificData.getDecoder(in));
  }

  @Override protected boolean hasCustomCoders() { return true; }

  @Override public void customEncode(org.apache.avro.io.Encoder out)
    throws java.io.IOException
  {
    out.writeString(this.animeName);

    out.writeInt(this.year);

    out.writeInt(this.rating);

  }

  @Override public void customDecode(org.apache.avro.io.ResolvingDecoder in)
    throws java.io.IOException
  {
    org.apache.avro.Schema.Field[] fieldOrder = in.readFieldOrderIfDiff();
    if (fieldOrder == null) {
      this.animeName = in.readString(this.animeName instanceof Utf8 ? (Utf8)this.animeName : null);

      this.year = in.readInt();

      this.rating = in.readInt();

    } else {
      for (int i = 0; i < 3; i++) {
        switch (fieldOrder[i].pos()) {
        case 0:
          this.animeName = in.readString(this.animeName instanceof Utf8 ? (Utf8)this.animeName : null);
          break;

        case 1:
          this.year = in.readInt();
          break;

        case 2:
          this.rating = in.readInt();
          break;

        default:
          throw new java.io.IOException("Corrupt ResolvingDecoder.");
        }
      }
    }
  }
}










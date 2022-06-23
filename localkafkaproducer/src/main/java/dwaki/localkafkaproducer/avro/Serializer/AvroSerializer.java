package dwaki.localkafkaproducer.avro.Serializer;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.BinaryEncoder;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.io.JsonEncoder;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.common.serialization.Serializer;

public class AvroSerializer<T extends SpecificRecordBase> implements Serializer<T> {

	@Override
	public void configure(Map<String, ?> configs, boolean isKey) {
		// do nothing
	}

	@Override
	public byte[] serialize(String topic, T payload) {
		byte[] bytes = null;
		try {
			if (payload != null) {
				ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
				BinaryEncoder binaryEncoder = EncoderFactory.get().binaryEncoder(byteArrayOutputStream, null);
				DatumWriter<GenericRecord> datumWriter = new GenericDatumWriter<>(payload.getSchema());
				datumWriter.write(payload, binaryEncoder);

				binaryEncoder.flush();
				byteArrayOutputStream.close();
				bytes = byteArrayOutputStream.toByteArray();
				// log.info("serialized payload='{}'", DatatypeConverter.printHexBinary(bytes));

				//AVRO to JSON
				// byte to datum
				DatumReader<Object> datumReader = new GenericDatumReader<>(payload.getSchema());
				Decoder decoder = DecoderFactory.get().binaryDecoder(bytes, null);
				GenericRecord avroDatum = (GenericRecord) datumReader.read(null, decoder);

				String json = null;
				try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
					DatumWriter<GenericRecord> writer = new GenericDatumWriter<>(payload.getSchema());
					JsonEncoder encoder = EncoderFactory.get().jsonEncoder(payload.getSchema(), baos, false);
					writer.write(avroDatum, encoder);
					encoder.flush();
					baos.flush();

					System.out.println(new String(baos.toByteArray(), StandardCharsets.UTF_8));
				}
			}
		} catch (Exception e) {
			System.out.println("Unable to serialize payload " + e);
		}
		return bytes;
	}

	@Override
	public void close() {
		// do nothing
	}
}

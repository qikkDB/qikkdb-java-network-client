// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: Message/InfoMessage.proto

package QikkDB.NetworkClient.Message;

public final class InfoMessageOuterClass {
  private InfoMessageOuterClass() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface InfoMessageOrBuilder extends
      // @@protoc_insertion_point(interface_extends:QikkDB.NetworkClient.Message.InfoMessage)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>.QikkDB.NetworkClient.Message.InfoMessage.StatusCode Code = 1;</code>
     * @return The enum numeric value on the wire for code.
     */
    int getCodeValue();
    /**
     * <code>.QikkDB.NetworkClient.Message.InfoMessage.StatusCode Code = 1;</code>
     * @return The code.
     */
    QikkDB.NetworkClient.Message.InfoMessageOuterClass.InfoMessage.StatusCode getCode();

    /**
     * <code>string Message = 2;</code>
     * @return The message.
     */
    java.lang.String getMessage();
    /**
     * <code>string Message = 2;</code>
     * @return The bytes for message.
     */
    com.google.protobuf.ByteString
        getMessageBytes();
  }
  /**
   * Protobuf type {@code QikkDB.NetworkClient.Message.InfoMessage}
   */
  public  static final class InfoMessage extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:QikkDB.NetworkClient.Message.InfoMessage)
      InfoMessageOrBuilder {
  private static final long serialVersionUID = 0L;
    // Use InfoMessage.newBuilder() to construct.
    private InfoMessage(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private InfoMessage() {
      code_ = 0;
      message_ = "";
    }

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstance(
        UnusedPrivateParameter unused) {
      return new InfoMessage();
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return this.unknownFields;
    }
    private InfoMessage(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      this();
      if (extensionRegistry == null) {
        throw new java.lang.NullPointerException();
      }
      com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuilder();
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            case 8: {
              int rawValue = input.readEnum();

              code_ = rawValue;
              break;
            }
            case 18: {
              java.lang.String s = input.readStringRequireUtf8();

              message_ = s;
              break;
            }
            default: {
              if (!parseUnknownField(
                  input, unknownFields, extensionRegistry, tag)) {
                done = true;
              }
              break;
            }
          }
        }
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(this);
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(
            e).setUnfinishedMessage(this);
      } finally {
        this.unknownFields = unknownFields.build();
        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return QikkDB.NetworkClient.Message.InfoMessageOuterClass.internal_static_QikkDB_NetworkClient_Message_InfoMessage_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return QikkDB.NetworkClient.Message.InfoMessageOuterClass.internal_static_QikkDB_NetworkClient_Message_InfoMessage_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              QikkDB.NetworkClient.Message.InfoMessageOuterClass.InfoMessage.class, QikkDB.NetworkClient.Message.InfoMessageOuterClass.InfoMessage.Builder.class);
    }

    /**
     * Protobuf enum {@code QikkDB.NetworkClient.Message.InfoMessage.StatusCode}
     */
    public enum StatusCode
        implements com.google.protobuf.ProtocolMessageEnum {
      /**
       * <code>OK = 0;</code>
       */
      OK(0),
      /**
       * <code>WAIT = 1;</code>
       */
      WAIT(1),
      /**
       * <code>GET_NEXT_RESULT = 6;</code>
       */
      GET_NEXT_RESULT(6),
      /**
       * <code>QUERY_ERROR = 2;</code>
       */
      QUERY_ERROR(2),
      /**
       * <code>IMPORT_ERROR = 3;</code>
       */
      IMPORT_ERROR(3),
      /**
       * <code>CONN_ESTABLISH = 4;</code>
       */
      CONN_ESTABLISH(4),
      /**
       * <code>CONN_END = 5;</code>
       */
      CONN_END(5),
      /**
       * <code>HEARTBEAT = 7;</code>
       */
      HEARTBEAT(7),
      UNRECOGNIZED(-1),
      ;

      /**
       * <code>OK = 0;</code>
       */
      public static final int OK_VALUE = 0;
      /**
       * <code>WAIT = 1;</code>
       */
      public static final int WAIT_VALUE = 1;
      /**
       * <code>GET_NEXT_RESULT = 6;</code>
       */
      public static final int GET_NEXT_RESULT_VALUE = 6;
      /**
       * <code>QUERY_ERROR = 2;</code>
       */
      public static final int QUERY_ERROR_VALUE = 2;
      /**
       * <code>IMPORT_ERROR = 3;</code>
       */
      public static final int IMPORT_ERROR_VALUE = 3;
      /**
       * <code>CONN_ESTABLISH = 4;</code>
       */
      public static final int CONN_ESTABLISH_VALUE = 4;
      /**
       * <code>CONN_END = 5;</code>
       */
      public static final int CONN_END_VALUE = 5;
      /**
       * <code>HEARTBEAT = 7;</code>
       */
      public static final int HEARTBEAT_VALUE = 7;


      public final int getNumber() {
        if (this == UNRECOGNIZED) {
          throw new java.lang.IllegalArgumentException(
              "Can't get the number of an unknown enum value.");
        }
        return value;
      }

      /**
       * @param value The numeric wire value of the corresponding enum entry.
       * @return The enum associated with the given numeric wire value.
       * @deprecated Use {@link #forNumber(int)} instead.
       */
      @java.lang.Deprecated
      public static StatusCode valueOf(int value) {
        return forNumber(value);
      }

      /**
       * @param value The numeric wire value of the corresponding enum entry.
       * @return The enum associated with the given numeric wire value.
       */
      public static StatusCode forNumber(int value) {
        switch (value) {
          case 0: return OK;
          case 1: return WAIT;
          case 6: return GET_NEXT_RESULT;
          case 2: return QUERY_ERROR;
          case 3: return IMPORT_ERROR;
          case 4: return CONN_ESTABLISH;
          case 5: return CONN_END;
          case 7: return HEARTBEAT;
          default: return null;
        }
      }

      public static com.google.protobuf.Internal.EnumLiteMap<StatusCode>
          internalGetValueMap() {
        return internalValueMap;
      }
      private static final com.google.protobuf.Internal.EnumLiteMap<
          StatusCode> internalValueMap =
            new com.google.protobuf.Internal.EnumLiteMap<StatusCode>() {
              public StatusCode findValueByNumber(int number) {
                return StatusCode.forNumber(number);
              }
            };

      public final com.google.protobuf.Descriptors.EnumValueDescriptor
          getValueDescriptor() {
        return getDescriptor().getValues().get(ordinal());
      }
      public final com.google.protobuf.Descriptors.EnumDescriptor
          getDescriptorForType() {
        return getDescriptor();
      }
      public static final com.google.protobuf.Descriptors.EnumDescriptor
          getDescriptor() {
        return QikkDB.NetworkClient.Message.InfoMessageOuterClass.InfoMessage.getDescriptor().getEnumTypes().get(0);
      }

      private static final StatusCode[] VALUES = values();

      public static StatusCode valueOf(
          com.google.protobuf.Descriptors.EnumValueDescriptor desc) {
        if (desc.getType() != getDescriptor()) {
          throw new java.lang.IllegalArgumentException(
            "EnumValueDescriptor is not for this type.");
        }
        if (desc.getIndex() == -1) {
          return UNRECOGNIZED;
        }
        return VALUES[desc.getIndex()];
      }

      private final int value;

      private StatusCode(int value) {
        this.value = value;
      }

      // @@protoc_insertion_point(enum_scope:QikkDB.NetworkClient.Message.InfoMessage.StatusCode)
    }

    public static final int CODE_FIELD_NUMBER = 1;
    private int code_;
    /**
     * <code>.QikkDB.NetworkClient.Message.InfoMessage.StatusCode Code = 1;</code>
     * @return The enum numeric value on the wire for code.
     */
    public int getCodeValue() {
      return code_;
    }
    /**
     * <code>.QikkDB.NetworkClient.Message.InfoMessage.StatusCode Code = 1;</code>
     * @return The code.
     */
    public QikkDB.NetworkClient.Message.InfoMessageOuterClass.InfoMessage.StatusCode getCode() {
      @SuppressWarnings("deprecation")
      QikkDB.NetworkClient.Message.InfoMessageOuterClass.InfoMessage.StatusCode result = QikkDB.NetworkClient.Message.InfoMessageOuterClass.InfoMessage.StatusCode.valueOf(code_);
      return result == null ? QikkDB.NetworkClient.Message.InfoMessageOuterClass.InfoMessage.StatusCode.UNRECOGNIZED : result;
    }

    public static final int MESSAGE_FIELD_NUMBER = 2;
    private volatile java.lang.Object message_;
    /**
     * <code>string Message = 2;</code>
     * @return The message.
     */
    public java.lang.String getMessage() {
      java.lang.Object ref = message_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        message_ = s;
        return s;
      }
    }
    /**
     * <code>string Message = 2;</code>
     * @return The bytes for message.
     */
    public com.google.protobuf.ByteString
        getMessageBytes() {
      java.lang.Object ref = message_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        message_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    private byte memoizedIsInitialized = -1;
    @java.lang.Override
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized == 1) return true;
      if (isInitialized == 0) return false;

      memoizedIsInitialized = 1;
      return true;
    }

    @java.lang.Override
    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      if (code_ != QikkDB.NetworkClient.Message.InfoMessageOuterClass.InfoMessage.StatusCode.OK.getNumber()) {
        output.writeEnum(1, code_);
      }
      if (!getMessageBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 2, message_);
      }
      unknownFields.writeTo(output);
    }

    @java.lang.Override
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (code_ != QikkDB.NetworkClient.Message.InfoMessageOuterClass.InfoMessage.StatusCode.OK.getNumber()) {
        size += com.google.protobuf.CodedOutputStream
          .computeEnumSize(1, code_);
      }
      if (!getMessageBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(2, message_);
      }
      size += unknownFields.getSerializedSize();
      memoizedSize = size;
      return size;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this) {
       return true;
      }
      if (!(obj instanceof QikkDB.NetworkClient.Message.InfoMessageOuterClass.InfoMessage)) {
        return super.equals(obj);
      }
      QikkDB.NetworkClient.Message.InfoMessageOuterClass.InfoMessage other = (QikkDB.NetworkClient.Message.InfoMessageOuterClass.InfoMessage) obj;

      if (code_ != other.code_) return false;
      if (!getMessage()
          .equals(other.getMessage())) return false;
      if (!unknownFields.equals(other.unknownFields)) return false;
      return true;
    }

    @java.lang.Override
    public int hashCode() {
      if (memoizedHashCode != 0) {
        return memoizedHashCode;
      }
      int hash = 41;
      hash = (19 * hash) + getDescriptor().hashCode();
      hash = (37 * hash) + CODE_FIELD_NUMBER;
      hash = (53 * hash) + code_;
      hash = (37 * hash) + MESSAGE_FIELD_NUMBER;
      hash = (53 * hash) + getMessage().hashCode();
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static QikkDB.NetworkClient.Message.InfoMessageOuterClass.InfoMessage parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static QikkDB.NetworkClient.Message.InfoMessageOuterClass.InfoMessage parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static QikkDB.NetworkClient.Message.InfoMessageOuterClass.InfoMessage parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static QikkDB.NetworkClient.Message.InfoMessageOuterClass.InfoMessage parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static QikkDB.NetworkClient.Message.InfoMessageOuterClass.InfoMessage parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static QikkDB.NetworkClient.Message.InfoMessageOuterClass.InfoMessage parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static QikkDB.NetworkClient.Message.InfoMessageOuterClass.InfoMessage parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static QikkDB.NetworkClient.Message.InfoMessageOuterClass.InfoMessage parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static QikkDB.NetworkClient.Message.InfoMessageOuterClass.InfoMessage parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static QikkDB.NetworkClient.Message.InfoMessageOuterClass.InfoMessage parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static QikkDB.NetworkClient.Message.InfoMessageOuterClass.InfoMessage parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static QikkDB.NetworkClient.Message.InfoMessageOuterClass.InfoMessage parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }

    @java.lang.Override
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder() {
      return DEFAULT_INSTANCE.toBuilder();
    }
    public static Builder newBuilder(QikkDB.NetworkClient.Message.InfoMessageOuterClass.InfoMessage prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }
    @java.lang.Override
    public Builder toBuilder() {
      return this == DEFAULT_INSTANCE
          ? new Builder() : new Builder().mergeFrom(this);
    }

    @java.lang.Override
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * Protobuf type {@code QikkDB.NetworkClient.Message.InfoMessage}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:QikkDB.NetworkClient.Message.InfoMessage)
        QikkDB.NetworkClient.Message.InfoMessageOuterClass.InfoMessageOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return QikkDB.NetworkClient.Message.InfoMessageOuterClass.internal_static_QikkDB_NetworkClient_Message_InfoMessage_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return QikkDB.NetworkClient.Message.InfoMessageOuterClass.internal_static_QikkDB_NetworkClient_Message_InfoMessage_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                QikkDB.NetworkClient.Message.InfoMessageOuterClass.InfoMessage.class, QikkDB.NetworkClient.Message.InfoMessageOuterClass.InfoMessage.Builder.class);
      }

      // Construct using QikkDB.NetworkClient.Message.InfoMessageOuterClass.InfoMessage.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(
          com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessageV3
                .alwaysUseFieldBuilders) {
        }
      }
      @java.lang.Override
      public Builder clear() {
        super.clear();
        code_ = 0;

        message_ = "";

        return this;
      }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return QikkDB.NetworkClient.Message.InfoMessageOuterClass.internal_static_QikkDB_NetworkClient_Message_InfoMessage_descriptor;
      }

      @java.lang.Override
      public QikkDB.NetworkClient.Message.InfoMessageOuterClass.InfoMessage getDefaultInstanceForType() {
        return QikkDB.NetworkClient.Message.InfoMessageOuterClass.InfoMessage.getDefaultInstance();
      }

      @java.lang.Override
      public QikkDB.NetworkClient.Message.InfoMessageOuterClass.InfoMessage build() {
        QikkDB.NetworkClient.Message.InfoMessageOuterClass.InfoMessage result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public QikkDB.NetworkClient.Message.InfoMessageOuterClass.InfoMessage buildPartial() {
        QikkDB.NetworkClient.Message.InfoMessageOuterClass.InfoMessage result = new QikkDB.NetworkClient.Message.InfoMessageOuterClass.InfoMessage(this);
        result.code_ = code_;
        result.message_ = message_;
        onBuilt();
        return result;
      }

      @java.lang.Override
      public Builder clone() {
        return super.clone();
      }
      @java.lang.Override
      public Builder setField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          java.lang.Object value) {
        return super.setField(field, value);
      }
      @java.lang.Override
      public Builder clearField(
          com.google.protobuf.Descriptors.FieldDescriptor field) {
        return super.clearField(field);
      }
      @java.lang.Override
      public Builder clearOneof(
          com.google.protobuf.Descriptors.OneofDescriptor oneof) {
        return super.clearOneof(oneof);
      }
      @java.lang.Override
      public Builder setRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          int index, java.lang.Object value) {
        return super.setRepeatedField(field, index, value);
      }
      @java.lang.Override
      public Builder addRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          java.lang.Object value) {
        return super.addRepeatedField(field, value);
      }
      @java.lang.Override
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof QikkDB.NetworkClient.Message.InfoMessageOuterClass.InfoMessage) {
          return mergeFrom((QikkDB.NetworkClient.Message.InfoMessageOuterClass.InfoMessage)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(QikkDB.NetworkClient.Message.InfoMessageOuterClass.InfoMessage other) {
        if (other == QikkDB.NetworkClient.Message.InfoMessageOuterClass.InfoMessage.getDefaultInstance()) return this;
        if (other.code_ != 0) {
          setCodeValue(other.getCodeValue());
        }
        if (!other.getMessage().isEmpty()) {
          message_ = other.message_;
          onChanged();
        }
        this.mergeUnknownFields(other.unknownFields);
        onChanged();
        return this;
      }

      @java.lang.Override
      public final boolean isInitialized() {
        return true;
      }

      @java.lang.Override
      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        QikkDB.NetworkClient.Message.InfoMessageOuterClass.InfoMessage parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (QikkDB.NetworkClient.Message.InfoMessageOuterClass.InfoMessage) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }

      private int code_ = 0;
      /**
       * <code>.QikkDB.NetworkClient.Message.InfoMessage.StatusCode Code = 1;</code>
       * @return The enum numeric value on the wire for code.
       */
      public int getCodeValue() {
        return code_;
      }
      /**
       * <code>.QikkDB.NetworkClient.Message.InfoMessage.StatusCode Code = 1;</code>
       * @param value The enum numeric value on the wire for code to set.
       * @return This builder for chaining.
       */
      public Builder setCodeValue(int value) {
        code_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>.QikkDB.NetworkClient.Message.InfoMessage.StatusCode Code = 1;</code>
       * @return The code.
       */
      public QikkDB.NetworkClient.Message.InfoMessageOuterClass.InfoMessage.StatusCode getCode() {
        @SuppressWarnings("deprecation")
        QikkDB.NetworkClient.Message.InfoMessageOuterClass.InfoMessage.StatusCode result = QikkDB.NetworkClient.Message.InfoMessageOuterClass.InfoMessage.StatusCode.valueOf(code_);
        return result == null ? QikkDB.NetworkClient.Message.InfoMessageOuterClass.InfoMessage.StatusCode.UNRECOGNIZED : result;
      }
      /**
       * <code>.QikkDB.NetworkClient.Message.InfoMessage.StatusCode Code = 1;</code>
       * @param value The code to set.
       * @return This builder for chaining.
       */
      public Builder setCode(QikkDB.NetworkClient.Message.InfoMessageOuterClass.InfoMessage.StatusCode value) {
        if (value == null) {
          throw new NullPointerException();
        }
        
        code_ = value.getNumber();
        onChanged();
        return this;
      }
      /**
       * <code>.QikkDB.NetworkClient.Message.InfoMessage.StatusCode Code = 1;</code>
       * @return This builder for chaining.
       */
      public Builder clearCode() {
        
        code_ = 0;
        onChanged();
        return this;
      }

      private java.lang.Object message_ = "";
      /**
       * <code>string Message = 2;</code>
       * @return The message.
       */
      public java.lang.String getMessage() {
        java.lang.Object ref = message_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          message_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string Message = 2;</code>
       * @return The bytes for message.
       */
      public com.google.protobuf.ByteString
          getMessageBytes() {
        java.lang.Object ref = message_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (java.lang.String) ref);
          message_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string Message = 2;</code>
       * @param value The message to set.
       * @return This builder for chaining.
       */
      public Builder setMessage(
          java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  
        message_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string Message = 2;</code>
       * @return This builder for chaining.
       */
      public Builder clearMessage() {
        
        message_ = getDefaultInstance().getMessage();
        onChanged();
        return this;
      }
      /**
       * <code>string Message = 2;</code>
       * @param value The bytes for message to set.
       * @return This builder for chaining.
       */
      public Builder setMessageBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
        
        message_ = value;
        onChanged();
        return this;
      }
      @java.lang.Override
      public final Builder setUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.setUnknownFields(unknownFields);
      }

      @java.lang.Override
      public final Builder mergeUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.mergeUnknownFields(unknownFields);
      }


      // @@protoc_insertion_point(builder_scope:QikkDB.NetworkClient.Message.InfoMessage)
    }

    // @@protoc_insertion_point(class_scope:QikkDB.NetworkClient.Message.InfoMessage)
    private static final QikkDB.NetworkClient.Message.InfoMessageOuterClass.InfoMessage DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new QikkDB.NetworkClient.Message.InfoMessageOuterClass.InfoMessage();
    }

    public static QikkDB.NetworkClient.Message.InfoMessageOuterClass.InfoMessage getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<InfoMessage>
        PARSER = new com.google.protobuf.AbstractParser<InfoMessage>() {
      @java.lang.Override
      public InfoMessage parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new InfoMessage(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<InfoMessage> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<InfoMessage> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public QikkDB.NetworkClient.Message.InfoMessageOuterClass.InfoMessage getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_QikkDB_NetworkClient_Message_InfoMessage_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_QikkDB_NetworkClient_Message_InfoMessage_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\031Message/InfoMessage.proto\022\034QikkDB.Netw" +
      "orkClient.Message\"\354\001\n\013InfoMessage\022B\n\004Cod" +
      "e\030\001 \001(\01624.QikkDB.NetworkClient.Message.I" +
      "nfoMessage.StatusCode\022\017\n\007Message\030\002 \001(\t\"\207" +
      "\001\n\nStatusCode\022\006\n\002OK\020\000\022\010\n\004WAIT\020\001\022\023\n\017GET_N" +
      "EXT_RESULT\020\006\022\017\n\013QUERY_ERROR\020\002\022\020\n\014IMPORT_" +
      "ERROR\020\003\022\022\n\016CONN_ESTABLISH\020\004\022\014\n\010CONN_END\020" +
      "\005\022\r\n\tHEARTBEAT\020\007b\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_QikkDB_NetworkClient_Message_InfoMessage_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_QikkDB_NetworkClient_Message_InfoMessage_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_QikkDB_NetworkClient_Message_InfoMessage_descriptor,
        new java.lang.String[] { "Code", "Message", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}

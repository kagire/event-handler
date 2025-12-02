package com.kagire.service.input;

import com.kagire.model.EventMessage;
import com.kagire.model.MessageType;
import com.kagire.service.db.EventMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class MessageGenerationMenu {

    private static EventMessageRepository eventMessageRepository;

    @Autowired
    public MessageGenerationMenu(EventMessageRepository eventMessageRepository) {
        MessageGenerationMenu.eventMessageRepository = eventMessageRepository;
    }

    private static MessageType type = MessageType.DEFAULT;
    private static int delayI = 0;
    private static final List<Double> delayOptions = List.of(5d, 10d, 15d);
    private static int expireI = 0;
    private static final List<Double> expireOptions = List.of(3d, 8d, 13d);
    private static int sendI = 0;
    private static final List<Double> sendOptions = List.of(3d, 8d, 15d);

    public static void menu() {
        Scanner myObj = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {

            String infoAboutCurrentState = "[Type: " + type + ", delay: " + getDelay() +
                "s, expire: " + getExpire() + "s, send: " + getSend() + "s]";

            System.out.println();
            System.out.println("Type any message or");
            System.out.println("1: to change message type (DEFAULT/EXPIRING)");
            System.out.println("2: to change 'delay' value (5s/10s/15s)");
            System.out.println("3: to change 'expire' value (3s/8s/13s)");
            System.out.println("4: to change 'send' value (3s/8s/15s)");
            System.out.println("q/quit/exit: to exit");
            System.out.println();
            System.out.println(infoAboutCurrentState);
            System.out.println();

            String input = myObj.nextLine();
            switch (input) {
                case "1" -> type = type == MessageType.DEFAULT ? MessageType.EXPIRING : MessageType.DEFAULT;
                case "2" -> delayI = getSwitchedOptionI(delayOptions, delayI);
                case "3" -> expireI = getSwitchedOptionI(expireOptions, expireI);
                case "4" -> sendI = getSwitchedOptionI(sendOptions, sendI);
                case "q", "quit", "exit" -> exit = true;
                default -> sendMessage(input);
            }
        }
    }

    private static double getDelay() {
        return delayOptions.get(delayI);
    }

    private static double getExpire() {
        return expireOptions.get(expireI);
    }

    private static double getSend() {
        return sendOptions.get(sendI);
    }

    private static int getSwitchedOptionI(List<Double> options, int current) {
        if (current + 1 >= options.size())
            return 0;
        else
            return current + 1;
    }

    private static void sendMessage(String messageBody) {
        eventMessageRepository.save(new EventMessage(type, getDelay(), getExpire(), getSend(), messageBody));
    }
}
